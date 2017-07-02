package com.tony_tang.android.demo.feature.note_list;

import com.jordifierro.androidbase.presentation.view.base.BaseListView;
import com.tony_tang.android.demo.common.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import hugo.weaving.DebugLog;

@DebugLog
@Module
public abstract class NoteListActivityModule {

    @ActivityScope
    @Binds
    abstract BaseListView provideMainView(NoteListActivity mainActivity);

    @ActivityScope
    @Binds
    abstract NoteEntityListModelController.ItemCommonClickListenerCallback provideItemCommonClickListenerCallback(NoteListActivity mainActivity);


}
