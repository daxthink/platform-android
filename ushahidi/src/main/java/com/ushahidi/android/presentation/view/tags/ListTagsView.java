package com.ushahidi.android.presentation.view.tags;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.TagModel;

import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface ListTagsView extends LoadDataView {

    /**
     * Render a tag list in the UI.
     *
     * @param tagModel The collection of {@link TagModel} that will be shown.
     */
    void renderTagList(List<TagModel> tagModel);
}
