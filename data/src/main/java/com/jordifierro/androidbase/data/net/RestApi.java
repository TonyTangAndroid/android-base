package com.jordifierro.androidbase.data.net;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntitiesWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestApi {

	String PARSE_REST_API_VALUE = "h0LF75nQ0YZhAJ1fQ57P3HksubAmPGsCnycW16M3";
	String PARSE_APPLICATION_ID_VALUE = "vstpbbwVyVsYYQaMTkYp8OWaRTf4XTMefcmSVAWP";
	String PARSE_SESSION_KEY = "X-Parse-Session-Token";


	String URL_BASE = "https://api.parse.com/1/";
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
	Observable<Response<CreatedWrapper>> createUser(@Body UserEntity userEntity);

	@DELETE(URL_PATH_USERS_OBJECT_ID)
	Observable<Response<EmptyWrapper>> deleteUser(@Header(PARSE_SESSION_KEY) String token, @Path("objectId") String objectId);

	@POST(URL_PATH_REQUEST_PASSWORD_RESET)
	Observable<Response<EmptyWrapper>> resetPassword(@Body Map<String, Object> params);

	@GET(URL_PATH_LOGIN)
	Observable<Response<UserEntity>> doLogin(@Query(FIELD_USERNAME) String username, @Query(FIELD_PASSWORD) String password);

	@POST(URL_PATH_LOGOUT)
	Observable<Response<EmptyWrapper>> doLogout(@Header(PARSE_SESSION_KEY) String token);

	@POST(URL_PATH_CLASSES_NOTE)
	Observable<Response<CreatedWrapper>> createNote(@Header(PARSE_SESSION_KEY) String token,
													@Body NoteEntity note);

	@GET(URL_PATH_CLASSES_NOTE_OBJECT_ID)
	Observable<Response<NoteEntity>> getNote(@Header(PARSE_SESSION_KEY) String token,
											 @Path("objectId") String objectId);

	@GET(URL_PATH_USERS_ME)
	Observable<Response<UserEntity>> me(@Header(PARSE_SESSION_KEY) String token);

	@GET(URL_PATH_CLASSES_NOTE)
	Observable<Response<NoteEntitiesWrapper>> getNotes(@Header(PARSE_SESSION_KEY) String token);

	@PUT(URL_PATH_CLASSES_NOTE_OBJECT_ID)
	Observable<Response<UpdatedWrapper>> updateNote(@Header(PARSE_SESSION_KEY) String token,
													@Path("objectId") String objectId, @Body NoteEntity note);

	@DELETE(URL_PATH_CLASSES_NOTE_OBJECT_ID)
	Observable<Response<EmptyWrapper>> deleteNote(@Header(PARSE_SESSION_KEY) String token,
										  @Path("objectId") String objectId);

}
