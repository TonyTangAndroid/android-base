package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteNoteUseCase extends CompletableUseCase {

    private NoteRepository noteRepository;

    private String noteObjectId;

    @Inject
    public DeleteNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public DeleteNoteUseCase setParams(String noteObjectId) {
        this.noteObjectId = noteObjectId;
        return this;
    }

    @Override
    protected Completable build() {
        return this.noteRepository.deleteNote(this.noteObjectId);
    }
}
