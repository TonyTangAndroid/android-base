package com.tony_tang.android.demo.feature.note_creation;

import com.tony_tang.android.demo.presentation.view.NoteCreateView;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteCreateModule {

    @Provides
    NoteCreateView provideNoteCreateView(NoteCreateFragment noteCreateFragment) {
        return noteCreateFragment;
    }

}
