package com.tony.tang.movie.ui.feature.list;


import com.tony.tang.movie.domain.MovieEntity;

import androidx.recyclerview.widget.DiffUtil;

final class MovieEntityDiffUtilItemCallback extends DiffUtil.ItemCallback<MovieEntity> {
    @Override
    public boolean areItemsTheSame(MovieEntity oldItem, MovieEntity newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(MovieEntity oldItem, MovieEntity newItem) {
        return true;
    }
}
