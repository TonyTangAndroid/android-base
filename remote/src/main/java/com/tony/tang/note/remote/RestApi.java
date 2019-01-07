package com.tony.tang.note.remote;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.entity.UserEntity;

import java.util.Map;

import javax.annotation.Nullable;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface RestApi {

    String PARSE_APPLICATION_ID_VALUE = "vstpbbwVyVsYYQaMTkYp8OWaRTf4XTMefcmSVAWP";

    String URL_BASE = "https://parse.anycopy.io/parse_server_demo/";
    String URL_PATH_USERS = "users";
    String URL_PATH_USERS_OBJECT_ID = "users/{objectId}";
    String URL_PATH_REQUEST_PASSWORD_RESET = "requestPasswordReset";
    String URL_PATH_LOGIN = "login";
    String URL_PATH_LOGOUT = "logout";
    String URL_PATH_CLASSES_NOTE_OBJECT_ID = "classes/note/{objectId}";
    String URL_PATH_USERS_ME = "users/me";
    String URL_PATH_CLASSES_NOTE = "classes/note";
    String FIELD_EMAIL = "email";
    String FIELD_USERNAME = "username";
    String FIELD_PASSWORD = "password";

    @POST(URL_PATH_USERS)
    Single<Response<CreatedWrapper>> createUser(@Body UserEntity userEntity);

    @DELETE(URL_PATH_USERS_OBJECT_ID)
    Single<Response<VoidEntity>> deleteUser(@Path("objectId") String objectId);

    @POST(URL_PATH_REQUEST_PASSWORD_RESET)
    Single<Response<VoidEntity>> resetPassword(@Body Map<String, Object> params);

    @GET(URL_PATH_LOGIN)
    Single<Response<UserEntity>> doLogin(@Query(FIELD_USERNAME) String username, @Query(FIELD_PASSWORD) String password);

    @POST(URL_PATH_LOGOUT)
    Single<Response<VoidEntity>> doLogout();

    @POST(URL_PATH_CLASSES_NOTE)
    Single<Response<CreatedWrapper>> createNote(@Body NoteData note);

    @GET(URL_PATH_CLASSES_NOTE_OBJECT_ID)
    Single<Response<NoteEntity>> getNote(
            @Path("objectId") String objectId);

    @GET(URL_PATH_USERS_ME)
    Single<Response<UserEntity>> getUserBySessionToken();

    @GET(URL_PATH_CLASSES_NOTE)
    Single<Response<NoteEntitiesWrapper>> getNotes(@Query("limit") int limit, @Nullable @Query(value = "where") String where);


    @PUT(URL_PATH_CLASSES_NOTE_OBJECT_ID)
    Single<Response<UpdatedWrapper>> updateNote(@Path("objectId") String objectId, @Body NoteData note);

    @DELETE(URL_PATH_CLASSES_NOTE_OBJECT_ID)
    Single<Response<VoidEntity>> deleteNote(@Path("objectId") String objectId);

}
