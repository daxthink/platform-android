package com.ushahidi.android.presentation.view.ui.activity;

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.di.components.post.DaggerGetPostComponent;
import com.ushahidi.android.presentation.di.components.post.GetPostComponent;
import com.ushahidi.android.presentation.view.ui.fragment.DetailPostFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.Bind;

/**
 * Details activity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DetailPostActivity extends BaseAppActivity implements HasComponent<GetPostComponent> {

    private static final String INTENT_EXTRA_PARAM_POST_ID
            = "com.ushahidi.android.INTENT_PARAM_POST_ID";

    private static final String INTENT_EXTRA_PARAM_POST_TITLE
            = "com.ushahidi.android.INTENT_PARAM_POST_TITLE";

    private static final String BUNDLE_STATE_PARAM_POST_TITLE
            = "com.ushahidi.android.STATE_PARAM_POST_TITLE";

    private static final String FRAG_TAG = "detail_post";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @Bind(R.id.backdrop)
    ImageView mBackdropImageView;

    private DetailPostFragment mDetailPostFragment;

    private GetPostComponent mGetPostComponent;

    private Long mPostId;

    private String mPostTitle;

    private ShareActionProvider mShareActionProvider;

    /**
     * Default constructor
     */
    public DetailPostActivity() {
        super(R.layout.activity_detail_post, R.menu.detail_post);
    }

    /**
     * Provides {@link Intent} for launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context, Long postId, String postTitle) {
        Intent intent = new Intent(context, DetailPostActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_POST_ID, postId);
        intent.putExtra(INTENT_EXTRA_PARAM_POST_TITLE, postTitle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        initialize(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(BUNDLE_STATE_PARAM_POST_TITLE, mPostTitle);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.menu_detail_post_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.menu_detail_post_share) {
            setShareIntent();
            return true;
        } else if (id == R.id.menu_detail_post_web) {
            openUrl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
                mPostId = getIntent().getLongExtra(SearchManager.EXTRA_DATA_KEY, -1);
            }
            mPostId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_POST_ID, -1);
            mPostTitle = getIntent().getStringExtra(INTENT_EXTRA_PARAM_POST_TITLE);
            mDetailPostFragment = (DetailPostFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAG_TAG);
            if (mDetailPostFragment == null) {
                mDetailPostFragment = DetailPostFragment.newInstance(mPostId);
                replaceFragment(R.id.detail_post_fragment_container, mDetailPostFragment);
            }
        } else {
            mPostTitle = savedInstanceState.getString(BUNDLE_STATE_PARAM_POST_TITLE);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbar.setTitle(mPostTitle);
    }

    private void injector() {
        mGetPostComponent = DaggerGetPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void setShareIntent() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        final String postUrl = getPostUrl();
        share.putExtra(Intent.EXTRA_TEXT, postUrl + mPostId);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(share);
        }
    }

    @NonNull
    private String getPostUrl() {
        return getAppComponent().prefsFactory().getActiveDeploymentUrl().get() + "/posts/"
                + mPostId;
    }

    private void openUrl() {
        final String postUrl = getPostUrl();
        final Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(postUrl));
        startActivity(i);
    }

    @Override
    public GetPostComponent getComponent() {
        return mGetPostComponent;
    }
}
