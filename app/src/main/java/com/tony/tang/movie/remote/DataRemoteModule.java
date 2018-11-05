package com.tony.tang.movie.remote;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.tony.tang.movie.AppScope;
import com.tony.tang.movie.app.AppConfig;
import com.tony.tang.movie.data.MovieEntityRemote;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {GsonModule.class, DataRemoteModule.OkHttpModule.class})
public abstract class DataRemoteModule {

    @Provides
    @AppScope
    static RestApi provideMovieRestApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }

    @Provides
    @AppScope
    static Retrofit provideRetrofit(
            AppConfig appConfig,
            OkHttpClient okHttpClient,
            GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(appConfig.serverUrl())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Binds
    @AppScope
    public abstract MovieEntityRemote bindNoteRemoteImpl(MovieEntityRemoteImpl movieRemoteRepository);

    @Module(includes = InterceptorModule.class)
    public static class OkHttpModule {

        @Provides
        @AppScope
        AuthInterceptor provideAuthInterceptor(AppConfig appConfig) {
            return new AuthInterceptor(appConfig.apiKey());
        }

        @Provides
        @AppScope
        OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                         HttpInterceptor httpInterceptor,
                                         AuthInterceptor authInterceptor,
                                         ChuckInterceptor chuckInterceptor) {
            return new OkHttpClient().newBuilder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(httpInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(chuckInterceptor)
                    .build();
        }
    }


}
