package com.tony_tang.android.demo.feature.note_list_paging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordifierro.androidbase.data.repository.NoteBean;
import com.tony_tang.android.demo.R;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import hugo.weaving.DebugLog;

@DebugLog
public class NoteBeanPagedListAdapter extends PagedListAdapter<NoteBean, RecyclerView.ViewHolder> {

    private final NoteBeanViewHolder.Listener listener;

    NoteBeanPagedListAdapter(@NonNull DiffUtil.ItemCallback<NoteBean> diffCallback, NoteBeanViewHolder.Listener listener) {
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
