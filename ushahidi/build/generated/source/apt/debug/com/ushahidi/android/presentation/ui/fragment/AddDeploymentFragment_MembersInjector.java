package com.ushahidi.android.presentation.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.presentation.presenter.AddDeploymentPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AddDeploymentFragment_MembersInjector implements MembersInjector<AddDeploymentFragment> {
  private final MembersInjector<BaseFragment> supertypeInjector;
  private final Provider<AddDeploymentPresenter> mAddDeploymentPresenterProvider;

  public AddDeploymentFragment_MembersInjector(MembersInjector<BaseFragment> supertypeInjector, Provider<AddDeploymentPresenter> mAddDeploymentPresenterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mAddDeploymentPresenterProvider != null;
    this.mAddDeploymentPresenterProvider = mAddDeploymentPresenterProvider;
  }

  @Override
  public void injectMembers(AddDeploymentFragment instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mAddDeploymentPresenter = mAddDeploymentPresenterProvider.get();
  }

  public static MembersInjector<AddDeploymentFragment> create(MembersInjector<BaseFragment> supertypeInjector, Provider<AddDeploymentPresenter> mAddDeploymentPresenterProvider) {  
      return new AddDeploymentFragment_MembersInjector(supertypeInjector, mAddDeploymentPresenterProvider);
  }
}

