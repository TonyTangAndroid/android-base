package com.jordifierro.androidbase.presentation.dependency.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.net.interceptor.HttpInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpInterceptorModule.class, GsonModule.class})
public class NetworkModule {


    @Provides
    @Singleton
    RestApi provideRestApi(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {

        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RestApi.class);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, HttpInterceptor httpInterceptor) {

        return new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(httpInterceptor)
                .build();
    }


}
