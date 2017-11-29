package com.tony_tang.android.demo.feature.note_detail;

import com.tony_tang.android.demo.presentation.view.NoteDetailView;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteDetailModule {

    //@DebugLog
    @Provides
    NoteDetailView provideDetailFragmentView(NoteDetailFragment noteDetailFragment) {
        return noteDetailFragment;
    }

}
