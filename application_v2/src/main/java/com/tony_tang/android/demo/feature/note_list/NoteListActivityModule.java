package com.tony_tang.android.demo.feature.note_list;

import com.jordifierro.androidbase.presentation.view.NoteListView;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

/**

 */
@Module
public class NoteListActivityModule {

    @DebugLog
    @Provides
    NoteListView provideMainView(NoteListActivity mainActivity) {
        return mainActivity;
    }

    @DebugLog
    @Provides
    NoteEntityListModelController.ItemClickListenerCallback provideItemClickListenerCallback(NoteListActivity mainActivity) {
        return mainActivity;
    }

}
