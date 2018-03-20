package com.tony_tang.android.demo.common.module;

import com.google.gson.Gson;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Component;
import retrofit2.converter.gson.GsonConverterFactory;
@Component(modules = GsonModule.class)
public interface GsonComponent {

    @ApplicationScope
    Gson gson();

    @ApplicationScope
    GsonConverterFactory gsonConverterFactory();

//    GsonBuilder gsonBuilder();

    @Component.Builder
    interface Builder {

        GsonComponent build();
    }
}