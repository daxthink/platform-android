package com.ushahidi.android.presentation.view.post;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.PostModel;

import java.util.List;

/**
 * View for displaying search results
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface SearchPostView extends LoadDataView {

    /**
     * Render a post list in the UI.
     *
     * @param postModel The collection of {@link PostModel} that will be shown.
     */
    void showSearchResult(List<PostModel> postModel);
}
