package com.tony.tang.note.ui.feature.note.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tony.tang.note.app.R;
import com.tony.tang.note.db.NoteBean;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import hugo.weaving.DebugLog;

public class NoteBeanPagedListAdapter extends PagedListAdapter<NoteBean, NoteBeanViewHolder> {

    private final NoteBeanViewHolder.Listener listener;

    NoteBeanPagedListAdapter(@NonNull DiffUtil.ItemCallback<NoteBean> diffCallback, NoteBeanViewHolder.Listener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteBeanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_paging, parent, false);
        return new NoteBeanViewHolder(rootView, listener);
    }

    @DebugLog
    @Override
    public void onBindViewHolder(@NonNull NoteBeanViewHolder holder, int position) {
        holder.bindTo(Objects.requireNonNull(getItem(position)));
    }

    @DebugLog
    @Override
    public void onViewRecycled(@NonNull NoteBeanViewHolder holder) {
        super.onViewRecycled(holder);
        holder.unbind();
    }

}
