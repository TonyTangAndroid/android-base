package com.jordifierro.androidbase.data.net;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntitiesWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestApi {

    String PARSE_APPLICATION_ID_VALUE = "vstpbbwVyVsYYQaMTkYp8OWaRTf4XTMefcmSVAWP";
    String PARSE_SESSION_KEY = "X-Parse-Session-Token";


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
    Single<Response<VoidEntity>> doLogout(@Header(PARSE_SESSION_KEY) String token);

    @POST(URL_PATH_CLASSES_NOTE)
    Single<Response<CreatedWrapper>> createNote(@Body NoteEntity note);

    @GET(URL_PATH_CLASSES_NOTE_OBJECT_ID)
    Single<Response<NoteEntity>> getNote(
            @Path("objectId") String objectId);

    @GET(URL_PATH_USERS_ME)
    Single<Response<UserEntity>> getUserBySessionToken(@Header(PARSE_SESSION_KEY) String token);

    @GET(URL_PATH_CLASSES_NOTE)
    Single<Response<NoteEntitiesWrapper>> getNotes(@Header(PARSE_SESSION_KEY) String token, @QueryMap Map<String, Object> queryParams);

    @PUT(URL_PATH_CLASSES_NOTE_OBJECT_ID)
    Single<Response<UpdatedWrapper>> updateNote(@Path("objectId") String objectId, @Body NoteEntity note);

    @DELETE(URL_PATH_CLASSES_NOTE_OBJECT_ID)
    Single<Response<VoidEntity>> deleteNote(@Path("objectId") String objectId);

}
