package com.tony.tang.note.app;

import android.app.Application;

import com.tony.tang.note.data.DataRemoteModule;
import com.tony.tang.note.db.NoteRoomDatabase;
import com.tony.tang.note.domain.interactor.note.CreateNoteUseCase;
import com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.remote.RemoteModule;
import com.tony.tang.note.ui.feature.note.creation.NoteCreateFragment;
import com.tony.tang.note.ui.feature.note.detail.NoteDetailFragment;

import dagger.BindsInstance;

@AppScope
@dagger.Component(modules = {
        DemoApplicationModule.class,
        PrefModule.class,
        ThreadModule.class,
        RemoteModule.GsonModule.class,
        DataRemoteModule.class})
public interface AppComponent {

    void inject(App app);

    NoteRoomDatabase database();

    CreateNoteUseCase createNoteUseCase();

    DeleteNoteUseCase deleteNoteUseCase();

    NoteDetailFragment.Component.Builder noteDetailComponentBuilder();

    NoteCreateFragment.Component.Builder noteCreationComponentBuilder();

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
