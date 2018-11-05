package com.tony.tang.movie.ui.feature.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tony.tang.movie.R;
import com.tony.tang.movie.domain.MovieEntity;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieEntityPagedListAdapter extends PagedListAdapter<MovieEntity, RecyclerView.ViewHolder> {

    private final MovieEntityPagingViewHolder.Listener listener;

    MovieEntityPagedListAdapter(@NonNull DiffUtil.ItemCallback<MovieEntity> diffCallback, MovieEntityPagingViewHolder.Listener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_paging, parent, false);
        return new MovieEntityPagingViewHolder(rootView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieEntityPagingViewHolder) holder).bindTo(getItem(position));
    }
}
