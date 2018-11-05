package com.tony.tang.movie.ui.feature.list;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tony.tang.movie.R;
import com.tony.tang.movie.domain.MovieEntity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

final class MovieEntityPagingViewHolder extends RecyclerView.ViewHolder {
    private final TextView tv_title;
    private final TextView tv_content;
    private final TextView tv_id;
    private final Button btn_delete;
    private final Button btn_toggle_title;
    private final Button btn_toggle_order;
    private final Listener listener;

    public MovieEntityPagingViewHolder(View rootView, Listener listener) {
        super(rootView);
        this.listener = listener;
        this.btn_delete = this.itemView.findViewById(R.id.btn_delete);
        this.btn_toggle_title = this.itemView.findViewById(R.id.btn_toggle_title);
        this.btn_toggle_order = this.itemView.findViewById(R.id.btn_toggle_order);
        this.tv_id = this.itemView.findViewById(R.id.tv_id);
        this.tv_title = this.itemView.findViewById(R.id.tv_title);
        this.tv_content = this.itemView.findViewById(R.id.tv_content);
    }

    public final void bindTo(@Nullable MovieEntity item) {
        if (item != null) {
            this.tv_id.setText(String.valueOf(item.getId()));
            this.tv_title.setText(item.getTitle());
            this.tv_content.setText(item.getOverview());
            this.btn_delete.setOnClickListener(view -> listener.delete(item));
            this.btn_toggle_title.setOnClickListener(view -> listener.toggleTitle(item));
            this.btn_toggle_order.setOnClickListener(view -> listener.toggleOrder(item));
        }
    }

    public interface Listener {
        void delete(MovieEntity noteEntity);

        void toggleTitle(MovieEntity noteEntity);

        void toggleOrder(MovieEntity noteEntity);
    }
}
