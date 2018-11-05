package com.tony.tang.movie.ui.feature.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tony.tang.movie.BuildConfig;
import com.tony.tang.movie.R;
import com.tony.tang.movie.domain.MovieEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

final class MovieEntitySearchAdapter extends RecyclerView.Adapter<MovieEntitySearchViewHolder> {

    private final Context context;
    private List<MovieEntity> dataList = new ArrayList<>();

    public MovieEntitySearchAdapter(Context context) {

        this.context = context;
    }

    public void updateDataSet(List<MovieEntity> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MovieEntitySearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent);
    }


    public MovieEntitySearchViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MovieEntitySearchViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieEntitySearchViewHolder holder, int position) {
        MovieEntity currentItem = dataList.get(position);
        holder.tv_title.setText(currentItem.getTitle());
        Glide.with(context).load(BuildConfig.IMAGE_SERVER_URL + currentItem.getPosterPath()).into(holder.iv_cover);

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

}