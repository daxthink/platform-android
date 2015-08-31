package com.ushahidi.android.presentation.util;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.TagModel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Utility class for building tag icon
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class TagUtility {

    private TagUtility() {
        // No instance
    }

    /**
     * Renders tags attached to a {@link com.ushahidi.android.presentation.model.PostModel} using
     * {@link IconDrawable} which is based on fontawesome to render icons. If the tags have Icons
     * it renders them otherwise it shows chips widgets
     *
     * @param context      The calling context
     * @param tagContainer The view group to hold the tag widgets
     * @param tag          Holds the individual tag widgets
     * @param tags         The tags to be rendered
     */
    public static void renderTagBade(@NonNull Context context, @NonNull ViewGroup tagContainer,
            @NonNull LinearLayout tag, List<TagModel> tags) {
        final int tagColorSize = context.getResources()
                .getDimensionPixelSize(R.dimen.tag_badge_color_size);
        final int tagIconSize = context.getResources()
                .getDimensionPixelSize(R.dimen.tag_icon_color_size);
        tagContainer.setVisibility(View.VISIBLE);
        // Remove all child views from the tags container otherwise
        // the previous items get appended when the recyclerview refreshes
        tag.removeAllViews();
        for (final TagModel tagModel : tags) {
            TextView tagBadge = (TextView) LayoutInflater.from(context)
                    .inflate(R.layout.include_tag_badge, tag, false);
            tagBadge.setText(tagModel.getTag());
            // Tag has both icon and color. Display both
            if (!TextUtils.isEmpty(tagModel.getIcon()) && Utility
                    .validateHexColor(tagModel.getColor())) {
                tagBadge.setCompoundDrawablesWithIntrinsicBounds(
                        getFontAwesomeIconAsDrawable(context, "fa_" + tagModel.getIcon(),
                                tagModel.getColor(), tagIconSize), null, null, null);

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
            } else {
                if (!TextUtils.isEmpty(tagModel.getIcon())) {
                    StringBuilder builder = new StringBuilder("fa_");
                    builder.append(tagModel.getIcon());
                    tagBadge.setCompoundDrawablesWithIntrinsicBounds(
                            getFontAwesomeIconAsDrawable(context, builder.toString(), null,
                                    tagIconSize),
                            null, null, null);
                }
            }

            tag.addView(tagBadge);
        }
    }

    private static Drawable getFontAwesomeIconAsDrawable(@NonNull Context context,
            @NonNull String fontawesomeIcon, @Nullable String color, int tagIconSize) {
        if (TextUtils.isEmpty(color)) {
            return new IconDrawable(context, Iconify.IconValue.valueOf(fontawesomeIcon))
                    .colorRes(R.color.black_dark).sizeDp(tagIconSize);
        }

        return new IconDrawable(context, Iconify.IconValue.valueOf(fontawesomeIcon))
                .color(Color.parseColor(color)).sizeDp(tagIconSize);
    }
}
