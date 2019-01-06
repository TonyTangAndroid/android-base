package com.tony.tang.note.ui.feature.note.list;


import com.tony.tang.note.db.NoteBean;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class NoteBeanDiffUtilItemCallback extends DiffUtil.ItemCallback<NoteBean> {
    @Override
    public boolean areItemsTheSame(@NonNull NoteBean oldItem, @NonNull NoteBean newItem) {
        return oldItem.objectId.equals(newItem.objectId);
    }

    @Override
    public boolean areContentsTheSame(@NonNull NoteBean oldItem, @NonNull NoteBean newItem) {
        return oldItem.content.equals(newItem.content);
    }
}
