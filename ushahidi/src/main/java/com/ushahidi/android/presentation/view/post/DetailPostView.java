package com.ushahidi.android.presentation.view.post;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.android.presentation.model.PostModel;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface DetailPostView extends LoadDataView {

    /**
     * Renders {@link com.ushahidi.android.presentation.model.PostModel}
     *
     * @param postModel The PostModel
     */
    void showPostModel(PostModel postModel);
}
