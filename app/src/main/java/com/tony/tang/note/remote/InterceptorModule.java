package com.tony.tang.note.remote;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.tony.tang.note.app.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class InterceptorModule {

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
}
