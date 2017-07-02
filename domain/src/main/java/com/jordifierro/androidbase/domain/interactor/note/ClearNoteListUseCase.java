package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class ClearNoteListUseCase extends UseCase<Long> {


    private GetNotesUseCase getNotesUseCase;
    private DeleteNoteUseCase deleteNoteUseCase;

    @Inject
    public ClearNoteListUseCase(ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread,
                                GetNotesUseCase getNotesUseCase,
                                DeleteNoteUseCase deleteNoteUseCase) {
        super(threadExecutor, postExecutionThread);
        this.getNotesUseCase = getNotesUseCase;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    @Override
    protected Observable<Long> buildUseCaseObservable() {

        return getNotesUseCase.buildUseCaseObservable().flatMap(Observable::fromIterable)
                .map(NoteEntity::getObjectId)
                .flatMap(this::delete)
                .count()
                .toObservable();


    }

    private ObservableSource<VoidEntity> delete(String objectId) {
        return deleteNoteUseCase.setParams(objectId).buildUseCaseObservable();
    }

}
