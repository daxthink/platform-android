// Generated code from Butter Knife. Do not modify!
package com.ushahidi.android.presentation.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AddDeploymentFragment$$ViewInjector<T extends com.ushahidi.android.presentation.ui.fragment.AddDeploymentFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558502, "field 'title'");
    target.title = finder.castView(view, 2131558502, "field 'title'");
    view = finder.findRequiredView(source, 2131558503, "field 'url'");
    target.url = finder.castView(view, 2131558503, "field 'url'");
    view = finder.findRequiredView(source, 2131558505, "method 'onClickValidate'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClickValidate();
        }
      });
    view = finder.findRequiredView(source, 2131558504, "method 'onClickCancel'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClickCancel();
        }
      });
  }

  @Override public void reset(T target) {
    target.title = null;
    target.url = null;
  }
}
