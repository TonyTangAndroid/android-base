package com.jordifierro.androidbase.data.remote;

import android.content.Context;

import com.jordifierro.androidbase.data.repository.NoteListRemote;
import com.jordifierro.androidbase.data.repository.NoteRemote;
import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.tony_tang.android.demo.common.module.DataLocalModule;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = DataRemoteModule.OkHttpModule.class)
public abstract class DataRemoteModule {

    @Provides
    @ApplicationScope
    static NoteRemoteImpl provideNoteRemoteImpl(RestApi restApi) {
        return new NoteRemoteImpl(restApi);
    }

    @Provides
    @ApplicationScope
    static RestApi provideRestApi(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(RestApi.class);
    }

    @Binds
    @ApplicationScope
    abstract NoteRemote bindNoteRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @ApplicationScope
    abstract NoteListRemote bindNoteListRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @ApplicationScope
    abstract UserRepository bindUserRepository(UserRemoteRepository userDataRepository);


    @Module(includes = DataLocalModule.class)
    public static class OkHttpModule {

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
        AuthInterceptor provideAuthInterceptor(TokenRepository tokenRepository) {
            return new AuthInterceptor(tokenRepository);
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
