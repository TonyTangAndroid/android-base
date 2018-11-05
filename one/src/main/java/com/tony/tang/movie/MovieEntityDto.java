package com.tony.tang.movie;


import java.util.List;

public class MovieEntityDto {


    private List<MovieEntity> results;
    private int page;
    private int total_pages;
    private int total_results;


    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {

        this.page = page;
    }

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }

    public int getTotal_pages() {

        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {

        this.total_pages = total_pages;
    }

    public int getTotal_results() {

        return this.total_results;
    }

    public void setTotal_results(int total_results) {

        this.total_results = total_results;
    }
}