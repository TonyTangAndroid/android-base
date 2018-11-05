package com.tony.tang.movie;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class InterceptorModule {

    @Provides
    @AppScope
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @AppScope
    ChuckInterceptor provideChuckInterceptor(Context context) {
        return new ChuckInterceptor(context);
    }

    @Provides
    @AppScope
    HttpInterceptor provideHttpInterceptor() {
        return new HttpInterceptor();
    }
}
