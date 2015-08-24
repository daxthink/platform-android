package com.ushahidi.android.data.database;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.data.database.converter.EnumEntityFieldConverter;
import com.ushahidi.android.data.database.converter.PostEntityConverter;
import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.entity.GeoJsonEntity;
import com.ushahidi.android.data.entity.PostEntity;
import com.ushahidi.android.data.entity.PostTagEntity;
import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.data.entity.UserEntity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverterFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Base Database helper class that all sqlite manipulation classes must inherit from
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class BaseDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ushahidi.db";

    private static final int DATABASE_VERSION = 2;

    private static final int LAST_DATABASE_NUKE_VERSION = 1;

    private static final Class[] ENTITIES = new Class[]{DeploymentEntity.class, UserEntity.class,
            TagEntity.class, PostTagEntity.class, PostEntity.class,
            GeoJsonEntity.class, FormEntity.class, FormAttributeEntity.class};

    private static final String TAG = BaseDatabaseHelper.class.getSimpleName();

    static {
        EntityConverterFactory factory = new EntityConverterFactory() {

            @Override
            public <T> EntityConverter<T> create(Cupboard cupboard, Class<T> type) {
                if (type == PostEntity.class) {
                    return (EntityConverter<T>) new PostEntityConverter(cupboard);
                }
                return null;
            }
        };
        CupboardFactory.setCupboard(new CupboardBuilder()
                .registerFieldConverter(UserEntity.Role.class,
                        new EnumEntityFieldConverter<>(UserEntity.Role.class))
                .registerFieldConverter(TagEntity.Type.class,
                        new EnumEntityFieldConverter<>(TagEntity.Type.class))
                .registerFieldConverter(PostEntity.Status.class,
                        new EnumEntityFieldConverter<>(PostEntity.Status.class))
                .registerFieldConverter(PostEntity.Type.class,
                        new EnumEntityFieldConverter<>(PostEntity.Type.class))
                .registerFieldConverter(FormAttributeEntity.Input.class,
                        new EnumEntityFieldConverter<>(FormAttributeEntity.Input.class))
                .registerFieldConverter(FormAttributeEntity.Type.class,
                        new EnumEntityFieldConverter<>(FormAttributeEntity.Type.class))
                .registerEntityConverterFactory(factory).useAnnotations().build());

        // Register our entities
        for (Class<?> clazz : ENTITIES) {
            cupboard().register(clazz);
        }
    }

    private boolean mIsClosed;

    /**
     * Default constructor
     *
     * @param context The calling context. Cannot be a null value
     */
    public BaseDatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        // This will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < LAST_DATABASE_NUKE_VERSION) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Nuking Database. Old Version: " + oldVersion);
            }
            cupboard().withDatabase(db).dropAllTables();
            onCreate(db);
        } else {
            // This will upgrade tables, adding columns and new tables.
            // Note that existing columns will not be converted
            cupboard().withDatabase(db).upgradeTables();
        }
    }

    /**
     * Close database connection
     */
    @Override
    public synchronized void close() {
        super.close();
        mIsClosed = true;
    }

    protected boolean isClosed() {
        return mIsClosed;

    }
}
