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

package com.ushahidi.android.presentation.state;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Event bus like using RxJava.
 *
 * Credits: http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class RxEventBus {

    private final Subject<Object, Object> mBus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        mBus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}
