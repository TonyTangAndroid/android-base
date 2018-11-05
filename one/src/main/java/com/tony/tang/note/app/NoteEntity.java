package com.tony.tang.note.app;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = NoteEntity.TABLE_NAME_NOTE_CACHE)
public class NoteEntity {

    public static final String TABLE_NAME_NOTE_CACHE = "note";
    public static final String OBJECT_ID = "id";


    @PrimaryKey
    public long id;

    public String overview;
    public String release_date;
    public String poster_path;


    public String original_title;
    public String original_language;
    public String title;
    public String backdrop_path;
    public int vote_count;


//    public int[] genre_ids;
//    public double popularity;
//    public double vote_average;
//    public boolean adult;
//    public boolean video;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntity that = (NoteEntity) o;
        return id == that.id &&
                vote_count == that.vote_count &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(release_date, that.release_date) &&
                Objects.equals(poster_path, that.poster_path) &&
                Objects.equals(original_title, that.original_title) &&
                Objects.equals(original_language, that.original_language) &&
                Objects.equals(title, that.title) &&
                Objects.equals(backdrop_path, that.backdrop_path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, overview, release_date, poster_path, original_title, original_language, title, backdrop_path, vote_count);
    }
}