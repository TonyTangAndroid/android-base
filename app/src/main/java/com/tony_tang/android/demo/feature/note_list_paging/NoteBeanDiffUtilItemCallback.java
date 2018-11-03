package com.tony_tang.android.demo.feature.note_list_paging;


import com.jordifierro.androidbase.data.repository.NoteBean;

import androidx.recyclerview.widget.DiffUtil;
import hugo.weaving.DebugLog;

@DebugLog
public class NoteBeanDiffUtilItemCallback extends DiffUtil.ItemCallback<NoteBean> {
    @Override
    public boolean areItemsTheSame(NoteBean oldItem, NoteBean newItem) {
        return oldItem.objectId.equals(newItem.objectId);
    }

    @Override
    public boolean areContentsTheSame(NoteBean oldItem, NoteBean newItem) {
        return oldItem.content.equals(newItem.content);
    }
}
