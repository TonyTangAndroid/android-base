package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ClearNoteListUseCase extends CompletableUseCase {


    private com.tony.tang.note.domain.interactor.note.GetNotesUseCase getNotesUseCase;
    private com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase deleteNoteUseCase;

    @Inject
    public ClearNoteListUseCase(ThreadExecutor threadExecutor,
                                UIThread UIThread,
                                com.tony.tang.note.domain.interactor.note.GetNotesUseCase getNotesUseCase,
                                com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase deleteNoteUseCase) {
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
