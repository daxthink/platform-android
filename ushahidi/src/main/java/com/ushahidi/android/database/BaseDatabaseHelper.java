package com.ushahidi.android.database;

import com.ushahidi.android.BuildConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import nl.qbusict.cupboard.annotation.Index;
import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.EntityConverter.Column;
import nl.qbusict.cupboard.convert.EntityConverter.ColumnType;
import nl.qbusict.cupboard.internal.IndexStatement;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Base Database helper class that all sqlite manipulation classes must inherit from
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class BaseDatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = BaseDatabaseHelper.class.getSimpleName();

    private boolean mIsClosed;

    private static final String DATABASE_NAME = "ushahidi.db";

    private static final int DATABASE_VERSION = 2;

    private static final int LAST_DATABASE_NUKE_VERSION = 1;

    protected abstract void setupTable();

    public BaseDatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        // This will ensure that all tables are created
        // Setup cupboard
        setupTable();
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        for (Class<?> entity : cupboard().getRegisteredEntities()) {
            // Attempt to create table only if the entity has been registered
            if (cupboard().getRegisteredEntities().contains(entity)) {
                EntityConverter<?> converter = cupboard().getEntityConverter(entity);
                createNewTable(db, converter.getTable(), converter.getColumns());
            }
        }
    }

    /**
     * Manually creating tables to ensure all individually registered
     */
    private boolean createNewTable(SQLiteDatabase db, String table, List<Column> cols) {
        StringBuilder sql = new StringBuilder("create table if not exists '").append(table).append(
                "' (_id integer primary key autoincrement");

        IndexStatement.Builder builder = new IndexStatement.Builder();
        for (Column col : cols) {
            if (col.type == ColumnType.JOIN) {
                continue;
            }
            String name = col.name;
            if (!name.equals(BaseColumns._ID)) {
                sql.append(", '").append(name).append("'");
                sql.append(" ").append(col.type.toString());
            }
            Index index = col.index;
            if (index != null) {
                builder.addIndexedColumn(table, name, index);
            }
        }
        sql.append(");");
        db.execSQL(sql.toString());

        for (IndexStatement stmt : builder.build()) {
            db.execSQL(stmt.getCreationSql(table));
        }
        return true;
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

    public boolean isClosed() {
        return mIsClosed;

    }
}
