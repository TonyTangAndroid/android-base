package com.tony.tang.note.ui.feature.note.list;


import android.app.Application;

import com.tony.tang.note.db.NoteBean;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import hugo.weaving.DebugLog;

@DebugLog
public class NoteBeanAndroidViewModel extends AndroidViewModel {
    private final LiveData<PagedList<NoteBean>> liveData;

    @Inject
    public NoteBeanAndroidViewModel(Application app, LiveData<PagedList<NoteBean>> liveData) {
        super(app);
        this.liveData = liveData;
    }

    public LiveData<PagedList<NoteBean>> get() {
        return liveData;
    }
}
