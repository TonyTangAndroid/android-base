package com.tony_tang.android.demo.feature.note_list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jordifierro.androidbase.presentation.view.NoteListView;
import com.tony_tang.android.demo.common.scope.ActivityScope;
import com.tony_tang.android.demo.feature.common.EndlessRecyclerOnScrollListenerTrial;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

@DebugLog
@Module
public class NoteListActivityModule {

    @ActivityScope
    @Provides
    NoteListView provideMainView(NoteListActivity mainActivity) {
        return mainActivity;
    }

    @ActivityScope
    @Provides
    NoteEntityListModelController.ItemClickListenerCallback provideItemClickListenerCallback(NoteListActivity mainActivity) {
        return mainActivity;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager(NoteListActivity mainActivity) {
        return new LinearLayoutManager(mainActivity);
    }

    @ActivityScope
    @Provides
    RecyclerView.OnScrollListener provideEndlessRecyclerOnScrollListenerProd(RecyclerView.LayoutManager layoutManager, NoteListActivity mainActivity) {
        return new EndlessRecyclerOnScrollListenerTrial(layoutManager, mainActivity);
    }


}
