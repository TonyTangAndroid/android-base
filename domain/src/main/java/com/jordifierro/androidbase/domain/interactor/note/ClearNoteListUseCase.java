package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ClearNoteListUseCase extends CompletableUseCase {


    private GetNotesUseCase getNotesUseCase;
    private DeleteNoteUseCase deleteNoteUseCase;

    @Inject
    public ClearNoteListUseCase(ThreadExecutor threadExecutor,
                                UIThread UIThread,
                                GetNotesUseCase getNotesUseCase,
                                DeleteNoteUseCase deleteNoteUseCase) {
        super(threadExecutor, UIThread);
        this.getNotesUseCase = getNotesUseCase;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    @Override
    protected Completable build() {

        throw new RuntimeException();


    }


    private Completable delete(String objectId) {
        return deleteNoteUseCase.setParams(objectId).build();
    }

}
