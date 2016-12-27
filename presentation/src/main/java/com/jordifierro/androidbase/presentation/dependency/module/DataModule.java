package com.jordifierro.androidbase.presentation.dependency.module;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.net.interceptor.HttpInterceptor;
import com.jordifierro.androidbase.data.repository.NoteDataRepository;
import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.data.repository.UserDataRepository;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    @Provides
    @Singleton
    SessionRepository provideSessionRepository(SharedPreferences sharedPreferences) {
        return new SessionDataRepository(sharedPreferences);
    }

    @Provides
    @Singleton
    RestApi provideRestApi() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(RestApi.class);
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(RestApi restApi) {
        return new UserDataRepository(restApi);
    }

    @Provides
    @Singleton
    NoteRepository provideNoteRepository(RestApi restApi) {
        return new NoteDataRepository(restApi);
    }

}
