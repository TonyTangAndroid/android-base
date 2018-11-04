/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tony.tang.note.domain.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Movie implements Serializable {

    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private Number[] genre_ids;
    private Number id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private Number popularity;
    private Number vote_count;
    private boolean video;
    private Number vote_average;


    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Number[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(Number[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public Number getVote_count() {
        return vote_count;
    }

    public void setVote_count(Number vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public void setVote_average(Number vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return adult == movie.adult &&
                video == movie.video &&
                Objects.equals(poster_path, movie.poster_path) &&
                Objects.equals(overview, movie.overview) &&
                Objects.equals(release_date, movie.release_date) &&
                Arrays.equals(genre_ids, movie.genre_ids) &&
                Objects.equals(id, movie.id) &&
                Objects.equals(original_title, movie.original_title) &&
                Objects.equals(original_language, movie.original_language) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(backdrop_path, movie.backdrop_path) &&
                Objects.equals(popularity, movie.popularity) &&
                Objects.equals(vote_count, movie.vote_count) &&
                Objects.equals(vote_average, movie.vote_average);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(poster_path, adult, overview, release_date, id, original_title, original_language, title, backdrop_path, popularity, vote_count, video, vote_average);
        result = 31 * result + Arrays.hashCode(genre_ids);
        return result;
    }
}
