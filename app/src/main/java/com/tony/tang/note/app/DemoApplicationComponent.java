package com.tony.tang.note.app;

import android.app.Application;

import com.tony.tang.note.data.DataRemoteModule;
import com.tony.tang.note.db.NoteRoomDatabase;
import com.tony.tang.note.domain.interactor.note.CreateNoteUseCase;
import com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.remote.RemoteModule;
import com.tony.tang.note.ui.feature.note.creation.NoteCreationSubComponent;
import com.tony.tang.note.ui.feature.note.detail.NoteDetailSubComponent;

import dagger.BindsInstance;
import dagger.Component;

@ApplicationScope
@Component(modules = {
        DemoApplicationModule.class,
        PrefModule.class,
        InMemoryRepoModule.class,
        ThreadModule.class,
        RemoteModule.GsonModule.class,
        DataRemoteModule.class})
public interface DemoApplicationComponent {


    void inject(DemoApplication app);

    NoteRoomDatabase database();

    CreateNoteUseCase createNoteUseCase();

    DeleteNoteUseCase deleteNoteUseCase();

    NoteDetailSubComponent.Builder noteDetailSubComponentBuilder();

    NoteCreationSubComponent.Builder noteCreationSubComponentBuilder();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        DemoApplicationComponent build();
    }
}
