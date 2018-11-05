package com.tony.tang.note.app;


import android.app.Application;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class NoteBeanAndroidViewModel extends AndroidViewModel {
    private LiveData<PagedList<NoteEntity>> noteBeanPageListLiveData;

    @Inject
    public NoteBeanAndroidViewModel(Application application) {
        super(application);


        NoteEntityDao dao = ((App) application).applicationComponent().database().noteBeanDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .build();
        noteBeanPageListLiveData = new LivePagedListBuilder<>(dao.allNoteBean(), config).build();

    }

    public LiveData<PagedList<NoteEntity>> get() {
        return noteBeanPageListLiveData;
    }


}
