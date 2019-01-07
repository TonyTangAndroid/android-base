package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ClearNoteListUseCase extends CompletableUseCase {


    private final NoteRepository noteRepository;
    private final DeleteNoteUseCase deleteNoteUseCase;

    @Inject
    public ClearNoteListUseCase(ThreadExecutor threadExecutor,
                                UIThread UIThread,
                                NoteRepository noteRepository,
                                DeleteNoteUseCase deleteNoteUseCase) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    @Override
    protected Completable build() {
        return noteRepository.objectIdList()
                .flatMapObservable(Observable::fromIterable)
                .flatMapCompletable(this::delete);
    }

    private Completable delete(String objectId) {
        return deleteNoteUseCase.setParams(objectId).build();
    }

}
