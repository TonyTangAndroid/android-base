package com.tony.tang.movie.ui.feature.list;


import android.app.Application;

import com.tony.tang.movie.app.App;
import com.tony.tang.movie.db.MovieEntityDao;
import com.tony.tang.movie.domain.MovieEntity;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

final  class MovieEntityAndroidViewModel extends AndroidViewModel {
    private LiveData<PagedList<MovieEntity>> noteBeanPageListLiveData;

    @Inject
    public MovieEntityAndroidViewModel(Application application) {
        super(application);


        MovieEntityDao dao = ((App) application).appComponent().database().noteBeanDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .build();
        noteBeanPageListLiveData = new LivePagedListBuilder<>(dao.allNoteBean(), config).build();

    }

    public LiveData<PagedList<MovieEntity>> get() {
        return noteBeanPageListLiveData;
    }


}
