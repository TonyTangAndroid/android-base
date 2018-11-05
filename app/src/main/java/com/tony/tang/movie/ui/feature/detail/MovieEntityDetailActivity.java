package com.tony.tang.movie.ui.feature.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tony.tang.movie.BuildConfig;
import com.tony.tang.movie.R;
import com.tony.tang.movie.domain.MovieEntity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import hugo.weaving.DebugLog;

public class MovieEntityDetailActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "extra_id";
    public ImageView iv_cover;
    public TextView tv_title;
    private long id;

    public static Intent constructIntent(Activity activity, long id) {
        return new Intent(activity, MovieEntityDetailActivity.class).putExtra(EXTRA_ID, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initParam();
        bindView();
        bindData();
    }

    private void initParam() {
        id = getIntent().getLongExtra(EXTRA_ID, 0);
    }

    private void bindData() {
        new MovieEntityDetailAndroidViewModel(getApplication(), id).get().observe(this, this::onDataReady);
    }

    @DebugLog
    private void onDataReady(MovieEntity currentItem) {
        tv_title.setText(currentItem.getTitle());
        Glide.with(this).load(BuildConfig.IMAGE_SERVER_URL + currentItem.getPosterPath()).into(iv_cover);
    }

    private void bindView() {
        iv_cover = findViewById(R.id.iv_cover);
        tv_title = findViewById(R.id.tv_title);
    }
}
