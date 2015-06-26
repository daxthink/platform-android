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

package com.ushahidi.android.presentation.ui.adapter;

import com.addhen.android.raiburari.presentation.ui.adapter.BaseRecyclerViewAdapter;
import com.addhen.android.raiburari.presentation.ui.widget.CapitalizedTextView;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.model.TagModel;
import com.ushahidi.android.presentation.ui.animators.ViewHelper;
import com.ushahidi.android.presentation.util.Utility;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Manages list of posts
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostAdapter extends BaseRecyclerViewAdapter<PostModel> {

    private int mDuration = 300;

    private int mLastPosition = -1;

    private float mFrom = 0f;

    private final View mEmptyView;

    public PostAdapter(final View emptyView) {
        mEmptyView = emptyView;
        onDataSetChanged();
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= getItems().size()
                : position < getItems().size()) && (customHeaderView != null ? position > 0
                : true)) {

            ((Widgets) viewHolder).title.setText(getItem(position).getTitle());
            ((Widgets) viewHolder).content.setText(getItem(position).getContent());
            //TODO: Remove this. Was for demo
            ((Widgets) viewHolder).renderImage(position);

            ((Widgets) viewHolder).date.setText(getRelativeTimeDisplay(
                    getItem(position).getCreated()));
            ((Widgets) viewHolder).status.setText(getItem(position).getStatus().name());
            //TODO: change hardcoded status type to an enum
            if (getItem(position).getStatus() == PostModel.Status.PUBLISHED) {
                ((Widgets) viewHolder).status.setTextColor(
                        ((Widgets) viewHolder).context.getResources().getColor(R.color.green));
            } else {
                ((Widgets) viewHolder).status.setBackgroundColor(
                        ((Widgets) viewHolder).context.getResources().getColor(R.color.red));
            }
            final List<TagModel> tags = getItem(position).getTags();
            if (!Utility.isCollectionEmpty(tags)) {
                ((Widgets) viewHolder).renderTagBadge(tags);
            } else {
                //Don't show post that don't have tags. Hide the horizontal scroll view otherwise
                // It shows tags from previous posts.
                ((Widgets) viewHolder).tagContainer.setVisibility(View.GONE);
            }

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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new Widgets(viewGroup.getContext(), LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_post_item, viewGroup, false));
    }

    @Override
    public int getAdapterItemCount() {
        return getItems().size();
    }

    @Override
    public void setItems(List<PostModel> items) {
        super.setItems(items);
        onDataSetChanged();
    }

    /**
     * Sets an empty view when the adapter's data item gets to zero
     */
    private void onDataSetChanged() {
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public void sortByDate() {
        Collections
                .sort(getItems(), (one, other) -> one.getCreated().compareTo(other.getCreated()));
        notifyDataSetChanged();
    }

    public void sortByTitle() {

        Collections.sort(getItems(), (one, other) -> one.getTitle().compareTo(other.getTitle()));

        notifyDataSetChanged();
    }

    protected Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }

    /**
     * Date into a relative time display
     */
    private String getRelativeTimeDisplay(Date pastTime) {
        long timeNow = System.currentTimeMillis();
        return DateUtils
                .getRelativeTimeSpanString(pastTime.getTime(), timeNow, DateUtils.MINUTE_IN_MILLIS)
                .toString();
    }

    public class Widgets extends RecyclerView.ViewHolder {

        @InjectView(R.id.post_title)
        TextView title;

        @InjectView(R.id.post_content)
        TextView content;

        @InjectView(R.id.post_date)
        TextView date;

        @InjectView(R.id.post_status)
        CapitalizedTextView status;

        @InjectView(R.id.post_image)
        ImageView postImage;

        @InjectView(R.id.post_tags)
        LinearLayout tag;

        @InjectView(R.id.post_tags_container)
        ViewGroup tagContainer;

        Context context;

        int tagColorSize;

        int tagIconSize;

        public Widgets(Context ctxt, View convertView) {
            super(convertView);
            ButterKnife.inject(convertView);
            this.context = ctxt;
            tagColorSize = this.context.getResources()
                    .getDimensionPixelSize(R.dimen.tag_badge_color_size);
            tagIconSize = this.context.getResources()
                    .getDimensionPixelSize(R.dimen.tag_icon_color_size);
        }

        public void renderTagBadge(List<TagModel> tags) {

            tagContainer.setVisibility(View.VISIBLE);
            //Remove all child views from the tags container otherwise
            //The previous items get appended when the recyclerview refreshes
            tag.removeAllViews();
            for (final TagModel tagModel : tags) {
                TextView tagBadge = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.include_tag_badge, tag, false);
                tagBadge.setText(tagModel.getTag());
                // Tag has both icon and color. Display both
                if (!TextUtils.isEmpty(tagModel.getIcon()) && Utility
                        .validateHexColor(tagModel.getColor())) {
                    StringBuilder builder = new StringBuilder("fa_");
                    builder.append(tagModel.getIcon());
                    tagBadge.setCompoundDrawablesWithIntrinsicBounds(
                            getFontAwesomeIconAsDrawable(builder.toString(), tagModel.getColor()),
                            null, null, null);

                    //Tag has only color, display badge
                } else if (Utility.validateHexColor(tagModel.getColor())) {
                    ShapeDrawable colorDrawable = new ShapeDrawable(new OvalShape());
                    colorDrawable.setIntrinsicWidth(tagColorSize);
                    colorDrawable.setIntrinsicHeight(tagColorSize);
                    colorDrawable.getPaint().setStyle(Paint.Style.FILL);
                    colorDrawable.getPaint().setColor(Color.parseColor(tagModel.getColor()));
                    tagBadge.setCompoundDrawablesWithIntrinsicBounds(colorDrawable,
                            null, null, null);

                    // Tag has only icon, display it
                } else if (!TextUtils.isEmpty(tagModel.getIcon())) {
                    StringBuilder builder = new StringBuilder("fa_");
                    builder.append(tagModel.getIcon());
                    tagBadge.setCompoundDrawablesWithIntrinsicBounds(
                            getFontAwesomeIconAsDrawable(builder.toString(), null),
                            null, null, null);
                }

                tag.addView(tagBadge);
            }
        }

        private Drawable getFontAwesomeIconAsDrawable(String fontawesomeIcon, String color) {
            if (TextUtils.isEmpty(color)) {
                return new IconDrawable(context, Iconify.IconValue.valueOf(fontawesomeIcon))
                        .colorRes(R.color.black_dark).sizeDp(tagIconSize);
            }

            return new IconDrawable(context, Iconify.IconValue.valueOf(fontawesomeIcon))
                    .color(Color.parseColor(color)).sizeDp(tagIconSize);
        }

        private void renderImage(int position) {
            // Seed dummy images
            Map<Integer, String> dummyImages = new HashMap();
            dummyImages.put(0,
                    "https://lh3.googleusercontent.com/-CGnI13j4vzM/VNYamMbbc5I/AAAAAAAAN3Q/AXIUMgluJrs/w1479-h832-no/2015-02-06%2B10.38.08%2B1.jpg");
            dummyImages.put(2, "https://farm8.staticflickr.com/7569/15110597684_e46a843af7_b.jpg");
            dummyImages.put(4, "https://farm9.staticflickr.com/8734/16863201508_5685055f10_b.jpg");
            dummyImages.put(5, "https://farm9.staticflickr.com/8800/16862037860_4bd562894e_b.jpg");
            dummyImages.put(6, "https://farm9.staticflickr.com/8786/17054994142_af68cc1df8_b.jpg");
            dummyImages.put(8, "https://farm8.staticflickr.com/7478/16028403009_d2eaa67d47_b.jpg");
            dummyImages.put(11, "https://farm9.staticflickr.com/8805/16870618028_7399699524_b.jpg");

            if (dummyImages.containsKey(position)) {
                Picasso.with(context).load(dummyImages.get(position))
                        .into(postImage, new Callback.EmptyCallback() {
                            @Override
                            public void onSuccess() {
                                postImage.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                postImage.setVisibility(View.GONE);
            }
        }
    }
}
