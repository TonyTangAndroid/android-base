package com.tony.tang.movie;


import androidx.recyclerview.widget.DiffUtil;

public class MovieEntityDiffUtilItemCallback extends DiffUtil.ItemCallback<MovieEntity> {
    @Override
    public boolean areItemsTheSame(MovieEntity oldItem, MovieEntity newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(MovieEntity oldItem, MovieEntity newItem) {
        return true;
    }
}