package com.tony_tang.android.demo.feature.note_list_paging;


import android.app.Application;

import com.jordifierro.androidbase.data.repository.NoteBean;
import com.jordifierro.androidbase.data.repository.NoteBeanDao;
import com.tony_tang.android.demo.common.DemoApplication;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class NoteBeanAndroidViewModel extends AndroidViewModel {
    private LiveData<PagedList<NoteBean>> noteBeanPageListLiveData;

    @Inject
    public NoteBeanAndroidViewModel(Application application) {
        super(application);


        NoteBeanDao dao = ((DemoApplication) application).applicationComponent().database().noteBeanDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .build();
        noteBeanPageListLiveData = new LivePagedListBuilder<>(dao.allNoteBean(), config).build();

    }

    public LiveData<PagedList<NoteBean>> get() {
        return noteBeanPageListLiveData;
    }


}
