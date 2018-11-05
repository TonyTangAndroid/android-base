package com.tony.tang.movie.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = MovieEntity.TABLE_NAME_NOTE_CACHE)
public class MovieEntity {

    public static final String TABLE_NAME_NOTE_CACHE = "movie";
    public static final String OBJECT_ID = "id";

    @PrimaryKey
    private long id;
    private String title;
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(posterPath, that.posterPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, overview, posterPath);
    }
}