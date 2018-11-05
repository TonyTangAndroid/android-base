package com.tony.tang.movie;


import android.app.Application;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class MovieEntityAndroidViewModel extends AndroidViewModel {
    private LiveData<PagedList<MovieEntity>> noteBeanPageListLiveData;

    @Inject
    public MovieEntityAndroidViewModel(Application application) {
        super(application);


        MovieEntityDao dao = ((App) application).applicationComponent().database().noteBeanDao();

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
