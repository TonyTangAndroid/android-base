package com.jordifierro.androidbase.application.dependency.module;

import com.jordifierro.androidbase.application.dependency.ApplicationScope;
import com.jordifierro.androidbase.data.net.RestApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpModule.class, GsonModule.class})
public class NetworkModule {


    @Provides

    @ApplicationScope
    RestApi provideRestApi(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {

        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RestApi.class);
    }


}
