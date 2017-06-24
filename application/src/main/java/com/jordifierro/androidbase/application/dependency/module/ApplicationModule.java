package com.jordifierro.androidbase.application.dependency.module;

import dagger.Module;

@Module(includes = {SharedPreferenceModule.class, ThreadModule.class})
public class ApplicationModule {

}
