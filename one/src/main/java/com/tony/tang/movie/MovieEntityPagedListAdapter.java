package com.tony.tang.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tony.tang.movie.R;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieEntityPagedListAdapter extends PagedListAdapter<MovieEntity, RecyclerView.ViewHolder> {

    private final MovieEntityViewHolder.Listener listener;

    MovieEntityPagedListAdapter(@NonNull DiffUtil.ItemCallback<MovieEntity> diffCallback, MovieEntityViewHolder.Listener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_paging, parent, false);
        return new MovieEntityViewHolder(rootView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieEntityViewHolder) holder).bindTo(getItem(position));
    }
}
