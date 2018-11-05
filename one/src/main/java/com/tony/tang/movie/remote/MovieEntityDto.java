package com.tony.tang.movie.remote;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.tony.tang.movie.domain.MovieEntity;

import java.util.List;

@AutoValue
abstract class MovieEntityDto {

    public static TypeAdapter<MovieEntityDto> typeAdapter(Gson gson) {
        return new AutoValue_MovieEntityDto.GsonTypeAdapter(gson);
    }

    @SerializedName("results")
    public abstract List<MovieEntity> getResults();

    @SerializedName("page")
    public abstract int getPage();

    @SerializedName("total_pages")
    public abstract int totalPages();

    @SerializedName("total_results")
    public abstract int totalResults();
}