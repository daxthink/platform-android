package com.ushahidi.android.presentation.view.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostFragmentStatePageAdapter extends BaseFragmentStatePageAdapter {

    private int mTotalPage;

    private List<Fragment> mFragmentPages;

    public AddPostFragmentStatePageAdapter(FragmentManager fragmentManager, int totalPage,
            List<Fragment> fragmentPages) {
        super(fragmentManager);
        mTotalPage = totalPage;
        mFragmentPages = fragmentPages;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentPages.get(position);
    }

    @Override
    public int getCount() {
        return mTotalPage;
    }
}
