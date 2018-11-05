package com.tony.tang.note.app;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RestApi {

    @GET("search/movie")
    Single<Response<MoviesWrapper>> searchRx(@Query("query") String query);


}
