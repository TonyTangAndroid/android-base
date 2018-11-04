package com.tony_tang.android.demo.common;

import android.app.Application;

import com.jordifierro.androidbase.domain.interactor.note.CreateNoteUseCase;
import com.jordifierro.androidbase.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.app.module.InMemoryRepoModule;
import com.tony.tang.note.app.module.PrefModule;
import com.tony.tang.note.app.module.ThreadModule;
import com.tony.tang.note.cache.NoteRoomDatabase;
import com.tony.tang.note.data.DataRemoteModule;
import com.tony.tang.note.remote.RemoteModule;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@Component(modules = {
        AndroidInjectionModule.class,
        DemoApplicationModule.class,
        PrefModule.class,
        InMemoryRepoModule.class,
        ThreadModule.class,
        RemoteModule.GsonModule.class,
        DataRemoteModule.class,
        DemoFragmentInjector.class})
public interface DemoApplicationComponent {

    void inject(DemoApplication app);

    NoteRoomDatabase database();

    CreateNoteUseCase createNoteUseCase();

    DeleteNoteUseCase deleteNoteUseCase();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        DemoApplicationComponent build();
    }
}
