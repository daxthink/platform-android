/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.data.api.account;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

/**
 * Implementation of {@link SessionManager} that persists user accounts using {@link
 * SharedPreferences}
 *
 * @param <T> The platform session
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PersistedSessionManager<T extends Session> implements SessionManager<T> {

    private static final int NUM_SESSIONS = 1;

    private static final String ACTIVE_SESSION_KEY = "active_session";

    private final SharedPreferences mSharedPreferences;

    private final String mPrefKeyActiveSession;

    private final String mPrefKeySession;

    private final SerializationStrategy<T> mSerializer;

    private final ConcurrentHashMap<String, T> mSessionMap;

    private final AtomicReference<T> mActiveSessionRef;

    private volatile boolean restorePending = true;

    /**
     * Default constructor
     *
     * @param sharedPreferences    The shared preferences
     * @param serializer           The serializer to be used for serialization
     * @param prefKeyActiveSession The active session preference key
     * @param prefKeySession       the preference key for the user's session
     */
    @Inject
    public PersistedSessionManager(SharedPreferences sharedPreferences,
            SerializationStrategy<T> serializer, String prefKeyActiveSession,
            String prefKeySession) {
        this(sharedPreferences, serializer, new ConcurrentHashMap<>(NUM_SESSIONS),
                prefKeyActiveSession, prefKeySession);
    }

    private PersistedSessionManager(SharedPreferences sharedPreferences,
            SerializationStrategy<T> serializer, ConcurrentHashMap<String, T> sessionMap,
            String prefKeyActiveSession, String prefKeySession) {
        mSharedPreferences = sharedPreferences;
        mSerializer = serializer;
        mSessionMap = sessionMap;
        mActiveSessionRef = new AtomicReference<>();
        mPrefKeyActiveSession = prefKeyActiveSession;
        mPrefKeySession = prefKeySession;
    }

    /**
     * Restore all session if necessary
     */
    void restoreAllSessionsIfNecessary() {
        // Only restore once
        if (restorePending) {
            restoreAllSessions();
        }
    }

    private synchronized void restoreAllSessions() {
        if (restorePending) {
            restoreActiveSession();
            restoreSessions();
            restorePending = false;
        }
    }

    private void restoreSessions() {
        T session;

        final Map<String, ?> preferences = mSharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : preferences.entrySet()) {
            if (isSessionPreferenceKey(entry.getKey())) {
                session = mSerializer.deserialize((String) entry.getValue());
                if (session != null) {
                    internalSetSession(session.getId(), session.getDeploymentId(), session, false);
                }
            }
        }

    }

    private void restoreActiveSession() {
        final T session = mSerializer
                .deserialize(mSharedPreferences.getString(ACTIVE_SESSION_KEY, ""));
        if (session != null) {
            internalSetSession(session.getId(), session.getDeploymentId(), session, false);
        }
    }

    /**
     * Determine if session's preference key exist
     *
     * @param preferenceKey The preference key
     * @return The preference key
     */
    boolean isSessionPreferenceKey(String preferenceKey) {
        return preferenceKey.startsWith(mPrefKeySession);
    }

    @Override
    public T getActiveSession() {
        restoreAllSessionsIfNecessary();
        return mActiveSessionRef.get();
    }


    @Override
    public void setActiveSession(@NonNull T session) {
        restoreAllSessionsIfNecessary();
        internalSetSession(session.getId(), session.getDeploymentId(), session, true);
    }

    @Override
    public void clearActiveSession() {
        restoreAllSessionsIfNecessary();
        if (mActiveSessionRef.get() != null) {
            clearSession(mActiveSessionRef.get().getId(),
                    mActiveSessionRef.get().getDeploymentId());
        }
    }

    @Override
    public T getSession(long id, long deploymentId) {
        restoreAllSessionsIfNecessary();
        return mSessionMap.get(getPrefKey(id, deploymentId));
    }

    @Override
    public void setSession(T session) {
        if (session == null) {
            throw new IllegalArgumentException("Session must not be null!");
        }
        restoreAllSessionsIfNecessary();
        internalSetSession(session.getId(), session.getDeploymentId(), session, false);
    }

    @Override
    public void clearSession(long id, long deploymentId) {
        restoreAllSessionsIfNecessary();
        if ((mActiveSessionRef.get() != null) && (mActiveSessionRef.get().getId() == id) && (
                mActiveSessionRef.get().getDeploymentId() == deploymentId)) {
            synchronized (this) {
                mActiveSessionRef.set(null);
                mSharedPreferences.edit().remove(mPrefKeyActiveSession).commit();
            }
        }

        mSessionMap.remove(getSessionMapKey(id, deploymentId));
    }

    @Override
    public Map<String, T> getSessionMap() {
        restoreAllSessionsIfNecessary();
        return Collections.unmodifiableMap(mSessionMap);
    }

    private void internalSetSession(long id, long deploymentId, T session, boolean forceUpdate) {
        mSessionMap.put(getSessionMapKey(id, deploymentId), session);
        mSharedPreferences.edit()
                .putString(getPrefKey(id, deploymentId), mSerializer.serialize(session))
                .commit();

        final T activeSession = mActiveSessionRef.get();
        if (activeSession == null || forceUpdate) {
            synchronized (this) {
                mActiveSessionRef.compareAndSet(activeSession, session);
                mSharedPreferences.edit()
                        .putString(ACTIVE_SESSION_KEY, mSerializer.serialize(session));
            }
        }
    }

    /**
     * Gets the preference key. It's a combination of separator, user id a separator and the
     * deployment associated with the user
     *
     * @param id           The session id which is the same as the user's id
     * @param deploymentId The deployment the session is tired to.
     * @return The preference key
     */
    String getPrefKey(long id, long deploymentId) {
        return mPrefKeySession + "_" + id + "_" + deploymentId;
    }

    /**
     * Gets the session map's key. It's a combination of separator, user id a separator and the
     * deployment associated with the user
     *
     * @param id           The session id which is the same as the user's id
     * @param deploymentId The deployment the session is tired to.
     * @return The session map key
     */
    String getSessionMapKey(long id, long deploymentId) {
        return mPrefKeySession + "_" + id + "_" + deploymentId;
    }
}
