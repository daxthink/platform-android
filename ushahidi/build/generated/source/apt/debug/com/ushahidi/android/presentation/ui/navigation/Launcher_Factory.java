package com.ushahidi.android.presentation.ui.navigation;

import android.app.Activity;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class Launcher_Factory implements Factory<Launcher> {
  private final Provider<Activity> activityProvider;

  public Launcher_Factory(Provider<Activity> activityProvider) {  
    assert activityProvider != null;
    this.activityProvider = activityProvider;
  }

  @Override
  public Launcher get() {  
    return new Launcher(activityProvider.get());
  }

  public static Factory<Launcher> create(Provider<Activity> activityProvider) {  
    return new Launcher_Factory(activityProvider);
  }
}

