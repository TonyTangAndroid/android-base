package com.tony.tang.movie;

import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {CacheModule.class, DataRemoteModule.OkHttpModule.class})
public abstract class DataRemoteModule {

    @Provides
    @AppScope
    static RestApi provideMovieRestApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }

    @Provides
    @AppScope
    static Retrofit provideRetrofit(
            @Named("movie_server_url") String url,
            OkHttpClient okHttpClient,
            GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Binds
    @AppScope
    abstract MovieEntityRepository provideNoteRepository(MovieEntityDataRepository noteDataRepository);

    @Binds
    @AppScope
    public abstract MovieEntityRemote bindNoteRemoteImpl(MovieEntityRemoteImpl movieRemoteRepository);

    @Module(includes = InterceptorModule.class)
    public static class OkHttpModule {

        @Provides
        @AppScope
        AuthInterceptor provideAuthInterceptor(@Named("movie_api_key") String apiKey) {
            return new AuthInterceptor(apiKey);
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