package com.tony_tang.android.demo.feature.note_list_paging;

import android.view.View;
import android.widget.TextView;

import com.jordifierro.androidbase.data.repository.NoteBean;
import com.tony_tang.android.demo.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import hugo.weaving.DebugLog;

@DebugLog
public final class NoteBeanViewHolder extends RecyclerView.ViewHolder {
    private final TextView tv_title;
    private final TextView tv_content;

    public final void bindTo(@Nullable NoteBean cheese) {
        if (cheese != null) {
            tv_title.setText(cheese.objectId);
            tv_content.setText(cheese.content);
        }
    }

    public NoteBeanViewHolder(View rootView) {
        super(rootView);
        this.tv_title = this.itemView.findViewById(R.id.tv_title);
        this.tv_content = this.itemView.findViewById(R.id.tv_content);
    }
}
