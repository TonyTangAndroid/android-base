package com.tony.tang.note.ui.feature.note.creation;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent(modules = {NoteCreateModule.class})
public interface NoteCreationSubComponent {

    void inject(NoteCreateFragment fragment);

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        Builder fragment(NoteCreateFragment fragment);

        NoteCreationSubComponent build();
    }
}
