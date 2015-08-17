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

package com.ushahidi.android.presentation.view.ui.adapter;

import com.addhen.android.raiburari.presentation.ui.adapter.BaseRecyclerViewAdapter;
import com.addhen.android.raiburari.presentation.ui.widget.CapitalizedTextView;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.model.TagModel;
import com.ushahidi.android.presentation.util.TagUtility;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.ui.animators.ViewHelper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Manages list of posts
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostAdapter extends BaseRecyclerViewAdapter<PostModel> {

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private int lastAnimatedPosition = -1;

    private int mDuration = 300;

    private int mLastPosition = -1;

    private float mFrom = 0f;


    /**
     * Adapters bind view holder
     *
     * @param viewHolder The view hold
     * @param position   The position of the item to be binded to the view
     */
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= getItems().size()
                : position < getItems().size()) && (customHeaderView != null ? position > 0
                : true)) {
            animateLoadedListItems(viewHolder.itemView, position);
            Widgets widgets = (Widgets) viewHolder;
            widgets.title.setText(getItem(position).getTitle());
            widgets.content.setText(getItem(position).getContent());

            widgets.date.setText(Utility.getRelativeTimeDisplay(
                    getItem(position).getCreated()));
            widgets.status.setText(getItem(position).getStatus().name());
            if (getItem(position).getStatus() == PostModel.Status.PUBLISHED) {
                widgets.status.setTextColor(widgets.context.getResources().getColor(R.color.green));
            } else {
                widgets.status
                        .setBackgroundColor(widgets.context.getResources().getColor(R.color.red));
            }
            final List<TagModel> tags = getItem(position).getTags();
            if (!Utility.isCollectionEmpty(tags)) {
                widgets.renderTagBadge(tags);
            } else {
                //Don't show post that don't have tags. Hide the horizontal scroll view otherwise
                // It shows tags from previous posts.
                widgets.tagContainer.setVisibility(View.GONE);
            }

            // Fade in items as user scrolls down the list
            if (position > mLastPosition) {
                for (Animator anim : getAnimators(viewHolder.itemView)) {
                    anim.setDuration(mDuration).start();
                }
                mLastPosition = position;
            } else {
                ViewHelper.clear(viewHolder.itemView);
            }
        }

    }

    private void animateLoadedListItems(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utility.getScreenHeight(view.getContext()));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new Widgets(viewGroup.getContext(), LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_post_item, viewGroup, false));
    }

    @Override
    public int getAdapterItemCount() {
        return getItems().size();
    }

    /**
     * Sort items by date
     */
    public void sortByDate() {
        Collections
                .sort(getItems(), (one, other) -> one.getCreated().compareTo(other.getCreated()));
        notifyDataSetChanged();
    }

    /**
     * Sort items by title
     */
    public void sortByTitle() {
        Collections.sort(getItems(), (one, other) -> one.getTitle().compareTo(other.getTitle()));
        notifyDataSetChanged();
    }

    /**
     * Gets the animated associated with the adapter's view
     *
     * @param view The view
     * @return The list of {@link Animator}
     */
    protected Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }

    /**
     * The view holder's widget
     */
    public class Widgets extends RecyclerView.ViewHolder {

        @Bind(R.id.post_title)
        TextView title;

        @Bind(R.id.post_content)
        TextView content;

        @Bind(R.id.post_date)
        TextView date;

        @Bind(R.id.post_status)
        CapitalizedTextView status;

        @Bind(R.id.post_image)
        ImageView postImage;

        @Bind(R.id.post_tags)
        LinearLayout tag;

        @Bind(R.id.post_tags_container)
        ViewGroup tagContainer;

        private Context context;

        /**
         * Widgets
         *
         * @param ctxt        The calling context
         * @param convertView The view
         */
        public Widgets(Context ctxt, View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            this.context = ctxt;
        }

        /**
         * Renders tag badges
         *
         * @param tags The tags
         */
        public void renderTagBadge(List<TagModel> tags) {
            TagUtility.renderTagBade(context, tagContainer, tag, tags);
        }
    }
}
