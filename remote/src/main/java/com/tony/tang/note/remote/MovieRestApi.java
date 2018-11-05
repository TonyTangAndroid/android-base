package com.tony.tang.note.remote;

import com.tony.tang.note.domain.entity.MoviesWrapper;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MovieRestApi {


    public static final String API_KEY = "87a901020f496977f9d6d508c5d186ec";
    String IMAGE = "http://image.tmdb.org/t/p/w780/";

    @GET("search/movie")
    Single<Response<MoviesWrapper>> searchRx(@Query("query") String query);


}
