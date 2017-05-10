package com.jordifierro.androidbase.presentation.dependency.module;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.presentation.dependency.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpModule.class, GsonModule.class})
public class NetworkModule {


    @Provides
    //@DebugLog
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
