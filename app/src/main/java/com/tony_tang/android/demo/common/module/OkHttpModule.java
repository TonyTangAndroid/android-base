package com.tony_tang.android.demo.common.module;

import android.content.Context;

import com.jordifierro.androidbase.data.net.interceptor.HttpInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpModule {


    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @ApplicationScope
    ChuckInterceptor provideChuckInterceptor(Context context) {
        return new ChuckInterceptor(context);
    }


    @Provides
    @ApplicationScope
    HttpInterceptor provideHttpInterceptor() {
        return new HttpInterceptor();
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                     HttpInterceptor httpInterceptor,
                                     ChuckInterceptor chuckInterceptor) {

        return new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(httpInterceptor)
                .addInterceptor(chuckInterceptor)
                .build();
    }
}
