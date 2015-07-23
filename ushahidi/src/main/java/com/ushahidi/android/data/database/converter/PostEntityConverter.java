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

package com.ushahidi.android.data.database.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ushahidi.android.data.entity.PostEntity;
import com.ushahidi.android.data.entity.PostValueEntity;

import java.lang.reflect.Field;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.ReflectiveEntityConverter;

/**
 * Makes it possible for {@link nl.qbusict.cupboard} to store the raw JSON
 * value for the {@link com.ushahidi.android.data.entity.PostEntity}
 * value field in the table without deserializing it.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostEntityConverter extends ReflectiveEntityConverter<PostEntity> {

    /**
     * Default constructor
     *
     * @param cupboard The {@link Cupboard} object
     */
    public PostEntityConverter(Cupboard cupboard) {
        super(cupboard, PostEntity.class);
    }

    @Override
    protected FieldConverter<?> getFieldConverter(Field field) {
        if ("mValues".equals(field.getName())) {
            return new PostValueEntityFieldConverter(new TypeToken<PostValueEntity>() {

            }.getType(), new Gson());
        }
        return super.getFieldConverter(field);
    }
}
