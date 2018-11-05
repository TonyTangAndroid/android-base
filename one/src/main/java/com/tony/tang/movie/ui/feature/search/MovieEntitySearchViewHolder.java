package com.tony.tang.movie.ui.feature.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tony.tang.movie.R;

import androidx.recyclerview.widget.RecyclerView;

final class MovieEntitySearchViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_cover;
    public TextView tv_title;

    public MovieEntitySearchViewHolder(View itemView) {
        super(itemView);
        bindView(itemView);
    }

    private void bindView(View itemView) {
        iv_cover = itemView.findViewById(R.id.iv_cover);
        tv_title = itemView.findViewById(R.id.tv_title);
    }
}
