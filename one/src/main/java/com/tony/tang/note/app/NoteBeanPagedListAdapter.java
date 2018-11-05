package com.tony.tang.note.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NoteBeanPagedListAdapter extends PagedListAdapter<NoteEntity, RecyclerView.ViewHolder> {

    private final NoteBeanViewHolder.Listener listener;

    NoteBeanPagedListAdapter(@NonNull DiffUtil.ItemCallback<NoteEntity> diffCallback, NoteBeanViewHolder.Listener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_paging, parent, false);
        return new NoteBeanViewHolder(rootView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NoteBeanViewHolder) holder).bindTo(getItem(position));
    }
}
