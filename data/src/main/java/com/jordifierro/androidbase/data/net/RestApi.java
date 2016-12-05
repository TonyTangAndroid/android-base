package com.jordifierro.androidbase.data.net;

import com.jordifierro.androidbase.data.net.wrapper.CreatedWrapper;
import com.jordifierro.androidbase.data.net.wrapper.NotesListWrapper;
import com.jordifierro.androidbase.data.net.wrapper.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RestApi {

	String PARSE_REST_API_VALUE = "h0LF75nQ0YZhAJ1fQ57P3HksubAmPGsCnycW16M3";
	String PARSE_APPLICATION_ID_VALUE = "vstpbbwVyVsYYQaMTkYp8OWaRTf4XTMefcmSVAWP";
	String PARSE_SESSION_KEY = "X-Parse-Session-Token";


	String URL_BASE = "https://api.parse.com/1/";

	@POST("users")
	Observable<Response<CreatedWrapper>> createUser(@Body UserEntity userEntity);

	@DELETE("users/{objectId}")
	Observable<Response<UpdatedWrapper>> deleteUser(@Header(PARSE_SESSION_KEY) String token, @Path("objectId") String objectId);

	@POST("requestPasswordReset")
	Observable<Response<Void>> resetPassword(@Field("email") String email);

	@FormUrlEncoded
	@POST("login")
	Observable<Response<UserEntity>> doLogin(@Field("username") String username, @Field("password") String password);

	@POST("logout")
	Observable<Response<Void>> doLogout(@Header(PARSE_SESSION_KEY) String token);

	@POST("classes/note")
	Observable<Response<CreatedWrapper>> createNote(@Header(PARSE_SESSION_KEY) String token,
													@Body NoteEntity note);

	@GET("classes/note/{objectId}")
	Observable<Response<NoteEntity>> getNote(@Header(PARSE_SESSION_KEY) String token,
											 @Path("objectId") String objectId);

	@GET("users/me")
	Observable<Response<UserEntity>> me(@Header(PARSE_SESSION_KEY) String token);

	@GET("classes/note")
	Observable<Response<NotesListWrapper>> getNotes(@Header(PARSE_SESSION_KEY) String token);

	@PUT("classes/note/{objectId}")
	Observable<Response<NoteEntity>> updateNote(@Header(PARSE_SESSION_KEY) String token,
												@Path("objectId") String objectId, @Body NoteEntity note);

	@DELETE("classes/note/{objectId}")
	Observable<Response<Void>> deleteNote(@Header(PARSE_SESSION_KEY) String token,
										  @Path("objectId") String objectId);

}
