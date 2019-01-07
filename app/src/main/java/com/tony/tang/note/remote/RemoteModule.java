package com.tony.tang.note.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.tony.tang.note.app.AppScope;
import com.tony.tang.note.data.DataLocalModule;
import com.tony.tang.note.data.NoteListDataRepository;
import com.tony.tang.note.data.NoteListRemote;
import com.tony.tang.note.data.NoteRemote;
import com.tony.tang.note.domain.entity.ArsenalAdapterFactory;
import com.tony.tang.note.domain.entity.GsonUTCDateAdapter;
import com.tony.tang.note.domain.entity.PermissionItemList;
import com.tony.tang.note.domain.repository.NoteListRepository;
import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.domain.repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = RemoteModule.OkHttpModule.class)
public abstract class RemoteModule {

    @Provides
    @Reusable
    public static NoteRemoteImpl provideNoteRemoteImpl(Gson gson, RestApi restApi) {
        return new NoteRemoteImpl(gson, restApi);
    }

    @Provides
    @AppScope
    static RestApi provideRestApi(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(RestApi.URL_BASE)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(RestApi.class);
    }

    @Binds
    @Reusable
    public abstract NoteRemote bindNoteRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @Reusable
    public abstract NoteListRemote bindNoteListRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @Reusable
    public abstract UserRepository bindUserRepository(UserRemoteRepository userDataRepository);

    @Binds
    @Reusable
    public abstract NoteListRepository bindNoteListRepository(NoteListDataRepository noteListDataRepository);


    @Module(includes = DataLocalModule.class)
    public static class OkHttpModule {

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
        AuthInterceptor provideAuthInterceptor(TokenRepository tokenRepository) {
            return new AuthInterceptor(tokenRepository);
        }

        @Provides
        @AppScope
        HttpInterceptor provideHttpInterceptor() {
            return new HttpInterceptor();
        }

        @Provides
        @Reusable
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

    @Module
    public static class GsonModule {


        @Reusable
        @Provides
        static DateFormat dateFormat() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat;
        }

        @Reusable
        @Provides
        static GsonConverterFactory getFactory(Gson gson) {
            return GsonConverterFactory.create(gson);
        }

        @Reusable
        @Provides
        static Gson getGson(ParseACLJsonAdapter parseACLJsonAdapter, GsonUTCDateAdapter gsonUTCDateAdapter) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapterFactory(WrapperAdapterFactory.create());
            gsonBuilder.registerTypeAdapterFactory(ArsenalAdapterFactory.create());
            gsonBuilder.registerTypeAdapter(PermissionItemList.class, parseACLJsonAdapter);
            gsonBuilder.registerTypeAdapter(Date.class, gsonUTCDateAdapter);
            return gsonBuilder.create();

        }
    }
}
