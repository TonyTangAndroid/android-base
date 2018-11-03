package com.tony_tang.android.demo.feature.note_list_paging;


import android.app.Application;

import com.jordifierro.androidbase.data.repository.NoteBean;
import com.jordifierro.androidbase.data.repository.NoteBeanDao;
import com.tony_tang.android.demo.common.DemoApplication;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;
import hugo.weaving.DebugLog;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

@DebugLog
public class NoteBeanAndroidViewModel extends AndroidViewModel {
    private Flowable<PagedList<NoteBean>> noteBeanPageListLiveData;

    @Inject
    public NoteBeanAndroidViewModel(Application application) {
        super(application);


        NoteBeanDao dao = ((DemoApplication) application).applicationComponent().database().noteBeanDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .build();
        noteBeanPageListLiveData = new RxPagedListBuilder<>(dao.allNoteBean(), config)
                .buildFlowable(BackpressureStrategy.LATEST);

    }

    public Flowable<PagedList<NoteBean>> get() {
        return noteBeanPageListLiveData;
    }


}
