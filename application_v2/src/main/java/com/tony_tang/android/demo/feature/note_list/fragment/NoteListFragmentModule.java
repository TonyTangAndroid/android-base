package com.tony_tang.android.demo.feature.note_list.fragment;

import com.jordifierro.androidbase.presentation.view.base.BaseListView;
import com.tony_tang.android.demo.common.scope.FragmentScope;
import com.tony_tang.android.demo.feature.common.BaseModelController;

import dagger.Binds;
import dagger.Module;
import hugo.weaving.DebugLog;

@DebugLog
@Module
public abstract class NoteListFragmentModule {

    @FragmentScope
    @Binds
    abstract BaseListView provideMainView(NoteListFragment noteListFragment);

    @FragmentScope
    @Binds
    abstract BaseModelController.ItemClickListenerCallback provideItemClickListenerCallback(NoteListFragment noteListFragment);


}
