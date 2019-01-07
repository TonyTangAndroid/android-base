package com.tony.tang.note.ui.feature.note.list;


import android.app.Application;

import com.tony.tang.note.app.App;
import com.tony.tang.note.app.AppComponent;
import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.db.NoteBeanDao;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class NoteBeanAndroidViewModel extends AndroidViewModel {
    private LiveData<PagedList<NoteBean>> noteBeanPageListLiveData;

    public NoteBeanAndroidViewModel(Application application) {
        super(application);
        AppComponent appComponent = ((App) application).appComponent();
        NoteBeanDao dao = appComponent.database().noteBeanDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10).setEnablePlaceholders(true).build();
        noteBeanPageListLiveData = new LivePagedListBuilder<>(dao.allNoteBean(), config)
                .setBoundaryCallback(appComponent.noteBeanBoundaryCallback()).build();
    }

    public LiveData<PagedList<NoteBean>> get() {
        return noteBeanPageListLiveData;
    }
}
