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

package com.ushahidi.android.data.entity;

import com.ushahidi.android.data.api.account.PlatformSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TestEntityFixtures {

    public static final Long DEPLOYMENT_ID = 1l;

    public static final Long ID = 2l;

    private static UserAccountEntity mUserAccountEntity;

    private static PlatformSession mPlatformSession;

    private static FormEntity mFormEntity;

    private static FormAttributeEntity mFormAttributeEntity;

    private static DeploymentEntity mDeploymentEntity;

    private static TagEntity mTagEntity;

    private TestEntityFixtures() {
        // No instances
    }

    public static UserAccountEntity getUserAccountEntity() {
        if (mUserAccountEntity == null) {
            mUserAccountEntity = new UserAccountEntity();
            mUserAccountEntity._id = ID;
            mUserAccountEntity.setPassword("password");
            mUserAccountEntity.setAccountName("account name");
        }
        return mUserAccountEntity;
    }

    public static PlatformSession getPlatformSession() {
        if (mPlatformSession == null) {
            mPlatformSession = new PlatformSession(1, "user_name", 1);
        }
        return mPlatformSession;
    }

    public static FormEntity getFormEntity() {
        if (mFormEntity == null) {
            mFormEntity = new FormEntity();
            mFormEntity._id = DEPLOYMENT_ID;
            mFormEntity.setCreated(new java.util.Date(1439502954));
            mFormEntity.setDescription("A Basic Form Entity");
            mFormEntity.setDeploymentId(1l);
            mFormEntity.setName("Basic form Entity");
            mFormEntity.setUpdated(new java.util.Date(1439503014));
            mFormEntity.setDisabled(true);
        }
        return mFormEntity;
    }

    public static List<FormAttributeEntity> getFormAttributeEntities() {
        List<FormAttributeEntity> formAttributeEntityList = new ArrayList<>();
        formAttributeEntityList.add(getFormAttributeEntity());
        return formAttributeEntityList;
    }

    public static FormAttributeEntity getFormAttributeEntity() {
        FormAttributeEntity formAttributeEntity = new FormAttributeEntity();
        formAttributeEntity._id = ID;
        formAttributeEntity.setDeploymentId(DEPLOYMENT_ID);
        formAttributeEntity.setCardinality(1);
        formAttributeEntity.setInput(FormAttributeEntity.Input.TEXT);
        formAttributeEntity.setType(FormAttributeEntity.Type.POINT);
        formAttributeEntity.setRequired(false);
        formAttributeEntity.setFormId(1l);
        formAttributeEntity.setLabel("Test varchar");
        formAttributeEntity.setKey("test_varchar");
        formAttributeEntity.setPriority(1);
        formAttributeEntity.setOptions(null);
        return formAttributeEntity;
    }

    public static DeploymentEntity getDeploymentEntity() {
        if (mDeploymentEntity == null) {
            mDeploymentEntity = new DeploymentEntity();
            mDeploymentEntity._id = ID;
            mDeploymentEntity.setName("Deployment Tititle");
            mDeploymentEntity.setStatus(DeploymentEntity.Status.ACTIVATED);
            mDeploymentEntity.setUrl("http://api.myushahidiinstance.com");
        }
        return mDeploymentEntity;
    }

    public static List<DeploymentEntity> getDeploymentEntityList() {
        List<DeploymentEntity> deploymentEntityList = new ArrayList<>();
        deploymentEntityList.add(getDeploymentEntity());
        return deploymentEntityList;
    }

    public static TagEntity getTagEntity() {
        if (mTagEntity == null) {
            mTagEntity = new TagEntity();
            mTagEntity._id = ID;
            mTagEntity.setDeploymentId(DEPLOYMENT_ID);
            mTagEntity.setCreated(new java.util.Date(1439502954));
            mTagEntity.setColor("#0c1404");
            mTagEntity.setType(TagEntity.Type.STATUS);
            mTagEntity.setIcon("icon");
            mTagEntity.setDescription("Positive reports");
            mTagEntity.setPriority(99);
            mTagEntity.setTag("tag");
        }
        return mTagEntity;
    }

    public static List<TagEntity> getTagEntities() {
        List<TagEntity> tagEntityList = new ArrayList<>();
        tagEntityList.add(getTagEntity());
        return tagEntityList;
    }
}
