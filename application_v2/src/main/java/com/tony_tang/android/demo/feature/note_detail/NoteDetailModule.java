package com.tony_tang.android.demo.feature.note_detail;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteDetailModule {

    @Provides
    NoteDetailView provideDetailFragmentView(NoteDetailFragment noteDetailFragment){
        return noteDetailFragment;
    }

}
