package com.jordifierro.androidbase.presentation.dependency.module;

import dagger.Module;

@Module(includes = {SharedPreferenceModule.class, ThreadModule.class})
public class ApplicationModule {

}
