package com.tony.tang.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tony.tang.movie.BuildConfig;
import com.tony.tang.movie.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MovieEntityAdapter extends RecyclerView.Adapter<MovieEntityAdapter.MovieViewHolder> {

    private final Context context;
    private List<MovieEntity> dataList = new ArrayList<>();

    public MovieEntityAdapter(Context context) {

        this.context = context;
    }

    public void updateDataSet(List<MovieEntity> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent);
    }


    public MovieViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieEntity currentItem = dataList.get(position);
        holder.tv_title.setText(currentItem.title);
        Glide.with(context).load(BuildConfig.IMAGE_SERVER_URL + currentItem.poster_path).into(holder.iv_cover);
        holder.tv_title.setText(currentItem.title);

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_cover;
        public TextView tv_title;

        public MovieViewHolder(View itemView) {
            super(itemView);
            bindView(itemView);
        }

        private void bindView(View itemView) {
            iv_cover = itemView.findViewById(R.id.iv_cover);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}