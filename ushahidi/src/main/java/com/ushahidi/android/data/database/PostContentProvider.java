package com.ushahidi.android.data.database;

import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.data.entity.PostEntity;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Posts {@link android.content.ContentProvider} for providing search suggestions
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostContentProvider extends SearchRecentSuggestionsProvider {

    /** The content provider authority's name */
    public static final String AUTHORITY = PostContentProvider.class
            .getName();

    /** Database mode **/
    public static final int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    private static final String TAG = PostContentProvider.class.getSimpleName();

    private static final String[] COLUMNS = {
            BaseColumns._ID, // must include this column
            SearchManager.SUGGEST_COLUMN_TEXT_1, // First line (title)
            SearchManager.SUGGEST_COLUMN_TEXT_2, // Second line (smaller text)
            SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA,
            SearchManager.SUGGEST_COLUMN_INTENT_ACTION,
            SearchManager.SUGGEST_COLUMN_SHORTCUT_ID
    };


    public PostContentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        String query = selectionArgs[0];
        if (query == null || query.length() == 0) {
            return null;
        }

        MatrixCursor cursor = new MatrixCursor(COLUMNS);

        try {
            List<PostEntity> list = getSearchResults(query);
            for (int i = 0; i < list.size(); i++) {
                cursor.addRow(createRow(i, list.get(i).getTitle(), list.get(i).getContent(),
                        list.get(i)._id));
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to lookup " + query, e);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    private Object[] createRow(Integer id, String text1, String text2, Long rowId) {
        return new Object[]{
                id, // _id
                text1, // text1
                text2, // text2
                rowId,
                "android.intent.action.VIEW", // action
                SearchManager.SUGGEST_NEVER_MAKE_SHORTCUT};
    }

    private List<PostEntity> getSearchResults(String query) {
        List<PostEntity> postEntities = new ArrayList<>();
        final PostDatabaseHelper mPostDatabaseHelper = new PostDatabaseHelper(getContext());
        if (!TextUtils.isEmpty(query)) {
            final String queryLowerCase = query.toLowerCase(Locale.getDefault());
            postEntities = mPostDatabaseHelper.searchQuery(
                    getPrefsFactoryInstance().getActiveDeploymentId().get(), queryLowerCase);
        }
        return postEntities;
    }

    /**
     * Not sure how to cleanly get {@link PrefsFactory} injected into the class via dagger so doing
     * that by hand
     *
     * @return An instance of PrefsFactory
     */
    private PrefsFactory getPrefsFactoryInstance() {
        SharedPreferences sharedPreferences = getContext().getApplicationContext()
                .getSharedPreferences("ushahidi-android-shared-prefs", MODE_PRIVATE);
        return new PrefsFactory(sharedPreferences);
    }
}
