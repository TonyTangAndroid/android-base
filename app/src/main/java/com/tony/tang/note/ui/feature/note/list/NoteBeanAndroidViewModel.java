package com.tony.tang.note.ui.feature.note.list;


import android.app.Application;

import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.db.NoteBeanDao;
import com.tony.tang.note.app.App;

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


        NoteBeanDao dao = ((App) application).appComponent().database().noteBeanDao();

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
