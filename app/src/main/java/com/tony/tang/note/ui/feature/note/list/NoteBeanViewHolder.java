package com.tony.tang.note.ui.feature.note.list;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tony.tang.note.app.R;
import com.tony.tang.note.app.Status;
import com.tony.tang.note.db.NoteBean;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public final class NoteBeanViewHolder extends RecyclerView.ViewHolder {
    private final TextView tv_title;
    private final TextView tv_content;
    private final Button btn_delete;
    private final CheckBox cb_star;
    private final Button btn_toggle_title;
    private final Button btn_toggle_order;
    private final Listener listener;

    public NoteBeanViewHolder(View rootView, Listener listener) {
        super(rootView);
        this.listener = listener;
        this.cb_star = this.itemView.findViewById(R.id.cb_star);
        this.btn_delete = this.itemView.findViewById(R.id.btn_delete);
        this.btn_toggle_title = this.itemView.findViewById(R.id.btn_toggle_title);
        this.btn_toggle_order = this.itemView.findViewById(R.id.btn_toggle_order);
        this.tv_title = this.itemView.findViewById(R.id.tv_title);
        this.tv_content = this.itemView.findViewById(R.id.tv_content);
    }

    public final void bindTo(@Nullable NoteBean item) {
        if (item != null) {
            this.tv_title.setText(item.objectId);
            this.tv_content.setText(item.content);
            this.cb_star.setChecked(item.status == Status.STAR);
            this.itemView.setOnClickListener(view -> listener.view(item));
            this.btn_delete.setOnClickListener(view -> listener.delete(item));
            this.btn_toggle_title.setOnClickListener(view -> listener.toggleTitle(item));
            this.btn_toggle_order.setOnClickListener(view -> listener.toggleOrder(item));
            this.cb_star.setOnCheckedChangeListener((buttonView, isChecked) -> listener.toggleStatus(isChecked, item.objectId));
        }
    }

    public interface Listener {
        void delete(NoteBean notebean);

        void toggleTitle(NoteBean notebean);

        void toggleOrder(NoteBean notebean);

        void toggleStatus(boolean isChecked, String objectId);

        void view(NoteBean item);
    }
}
