package com.tony.tang.movie.ui.feature.detail;


import android.app.Application;

import com.tony.tang.movie.app.App;
import com.tony.tang.movie.db.MovieEntityDao;
import com.tony.tang.movie.domain.MovieEntity;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

final class MovieEntityDetailAndroidViewModel extends AndroidViewModel {
    private final LiveData<MovieEntity> liveData;

    @Inject
    public MovieEntityDetailAndroidViewModel(Application application, long id) {
        super(application);
        MovieEntityDao dao = ((App) application).appComponent().database().noteBeanDao();
        liveData = dao.find(id);
    }

    public LiveData<MovieEntity> get() {
        return liveData;
    }


}
