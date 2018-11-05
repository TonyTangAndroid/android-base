package com.tony.tang.note.app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public final class NoteBeanViewHolder extends RecyclerView.ViewHolder {
    private final TextView tv_title;
    private final TextView tv_content;
    private final TextView tv_id;
    private final Button btn_delete;
    private final Button btn_toggle_title;
    private final Button btn_toggle_order;
    private final Listener listener;

    public NoteBeanViewHolder(View rootView, Listener listener) {
        super(rootView);
        this.listener = listener;
        this.btn_delete = this.itemView.findViewById(R.id.btn_delete);
        this.btn_toggle_title = this.itemView.findViewById(R.id.btn_toggle_title);
        this.btn_toggle_order = this.itemView.findViewById(R.id.btn_toggle_order);
        this.tv_id = this.itemView.findViewById(R.id.tv_id);
        this.tv_title = this.itemView.findViewById(R.id.tv_title);
        this.tv_content = this.itemView.findViewById(R.id.tv_content);
    }

    public final void bindTo(@Nullable NoteEntity item) {
        if (item != null) {
            this.tv_id.setText(String.valueOf(item.id));
            this.tv_title.setText(item.title);
            this.tv_content.setText(item.overview);
            this.btn_delete.setOnClickListener(view -> listener.delete(item));
            this.btn_toggle_title.setOnClickListener(view -> listener.toggleTitle(item));
            this.btn_toggle_order.setOnClickListener(view -> listener.toggleOrder(item));
        }
    }

    public interface Listener {
        void delete(NoteEntity noteEntity);

        void toggleTitle(NoteEntity noteEntity);

        void toggleOrder(NoteEntity noteEntity);
    }
}
