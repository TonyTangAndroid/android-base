package com.tony.tang.note.ui.feature.note.detail;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent(modules = {NoteDetailModule.class})
public interface NoteDetailSubComponent {

    void inject(NoteDetailFragment fragment);

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        Builder fragment(NoteDetailFragment fragment);

        NoteDetailSubComponent build();
    }
}
