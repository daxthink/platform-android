package com.ushahidi.android.data.repository.datasource.deployment;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public enum DeploymentDataSourceFactory_Factory implements Factory<DeploymentDataSourceFactory> {
INSTANCE;

  @Override
  public DeploymentDataSourceFactory get() {  
    return new DeploymentDataSourceFactory();
  }

  public static Factory<DeploymentDataSourceFactory> create() {  
    return INSTANCE;
  }
}

