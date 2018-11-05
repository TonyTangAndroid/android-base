package com.tony.tang.note.remote;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.tony.tang.note.app.ApplicationScope;
import com.tony.tang.note.data.DataLocalModule;
import com.tony.tang.note.data.NoteListRemote;
import com.tony.tang.note.data.NoteRemote;
import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.domain.repository.UserRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {RemoteModule.OkHttpModule.class})
public abstract class RemoteModule {

    @Provides
    @ApplicationScope
    public static NoteRemoteImpl provideNoteRemoteImpl(RestApi restApi) {
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
    public abstract NoteRemote bindNoteRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @ApplicationScope
    public abstract NoteListRemote bindNoteListRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @ApplicationScope
    public abstract UserRepository bindUserRepository(UserRemoteRepository userDataRepository);


    @Module(includes = {DataLocalModule.class, InterceptorModule.class})
    public static class OkHttpModule {


        @Provides
        @ApplicationScope
        AuthInterceptor provideAuthInterceptor(TokenRepository tokenRepository) {
            return new AuthInterceptor(tokenRepository);
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
