package com.tony.tang.note.app;

import android.app.Application;

import com.tony.tang.note.data.DataRemoteModule;
import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.db.NoteRoomDatabase;
import com.tony.tang.note.domain.interactor.note.ClearNoteListUseCase;
import com.tony.tang.note.domain.interactor.note.CreateNoteUseCase;
import com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.domain.interactor.note.GenerateNoteListUseCase;
import com.tony.tang.note.domain.interactor.note.UpdateNoteUseCase;
import com.tony.tang.note.remote.RemoteModule;
import com.tony.tang.note.ui.feature.note.creation.NoteCreateFragment;
import com.tony.tang.note.ui.feature.note.detail.NoteDetailFragment;
import com.tony.tang.note.ui.feature.note.edit.NoteEditFragment;
import com.tony.tang.note.ui.feature.note.list.LiveDataModule;
import com.tony.tang.note.ui.feature.note.list.NoteBeanBoundaryCallback;
import com.tony.tang.note.ui.feature.note.list.ViewModelFactory;
import com.tony.tang.note.ui.feature.note.list.ViewModelModule;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import dagger.BindsInstance;

@AppScope
@dagger.Component(modules = {
        DemoApplicationModule.class,
        PrefModule.class,
        ThreadModule.class,
        RemoteModule.GsonModule.class,
        ViewModelModule.class,
        LiveDataModule.class,
        DataRemoteModule.class})
public interface AppComponent {

    void inject(App app);

    NoteRoomDatabase database();

    NoteBeanBoundaryCallback noteBeanBoundaryCallback();

    CreateNoteUseCase createNoteUseCase();

    UpdateNoteUseCase updateNoteUseCase();

    ClearNoteListUseCase clearNoteListUseCase();

    GenerateNoteListUseCase generateNoteListUseCase();

    DeleteNoteUseCase deleteNoteUseCase();

    NoteDetailFragment.Component.Builder noteDetailComponentBuilder();

    NoteCreateFragment.Component.Builder noteCreationComponentBuilder();

    NoteEditFragment.Component.Builder noteEditComponentBuilder();

    ViewModelFactory viewModelFactory();

    LiveData<PagedList<NoteBean>> liveData();

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
