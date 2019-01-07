package com.tony.tang.note.ui.feature.note.list;


import com.tony.tang.note.app.AppScope;
import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.db.NoteBeanDao;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import dagger.Module;
import dagger.Provides;

@Module
public class LiveDataModule {

    @AppScope
    @Provides
    PagedList.Config pageConfig() {
        return new PagedList.Config.Builder()
                .setPageSize(10).setEnablePlaceholders(true).build();
    }


    @Provides
    LiveData<PagedList<NoteBean>> liveData(NoteBeanDao dao,
                                           PagedList.Config config,
                                           NoteBeanBoundaryCallback boundaryCallback) {
        return new LivePagedListBuilder<>(dao.allNoteBean(), config)
                .setBoundaryCallback(boundaryCallback).build();
    }

}
