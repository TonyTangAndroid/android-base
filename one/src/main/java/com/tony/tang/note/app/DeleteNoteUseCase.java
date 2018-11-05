package com.tony.tang.note.app;


import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteNoteUseCase extends CompletableUseCase {

    private NoteRepository noteRepository;

    private long id;

    @Inject
    public DeleteNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public DeleteNoteUseCase setParams(long id) {
        this.id = id;
        return this;
    }

    @Override
    protected Completable build() {
        return this.noteRepository.delete(id);
    }
}
