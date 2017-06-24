package com.jordifierro.androidbase.application.dependency.module;

import dagger.Module;

@Module(includes = {DataNetworkModule.class, DataLocalModule.class})
public class DataModule {


}
