package com.tony.tang.note.app;


import androidx.recyclerview.widget.DiffUtil;

public class NoteBeanDiffUtilItemCallback extends DiffUtil.ItemCallback<NoteEntity> {
    @Override
    public boolean areItemsTheSame(NoteEntity oldItem, NoteEntity newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(NoteEntity oldItem, NoteEntity newItem) {
        return true;
    }
}
