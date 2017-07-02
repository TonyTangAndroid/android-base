package com.tony_tang.android.demo.feature.note_creation;

import com.jordifierro.androidbase.presentation.view.NoteCreateView;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

@Module
public class NoteCreateModule {

    @DebugLog
    @Provides
    NoteCreateView provideNoteCreateView(NoteCreateFragment noteCreateFragment) {
        return noteCreateFragment;
    }

}
