package com.tony.tang.movie;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RestApi {

    @GET("search/movie")
    Single<Response<MovieEntityDto>> searchRx(@Query("query") String query);


}
