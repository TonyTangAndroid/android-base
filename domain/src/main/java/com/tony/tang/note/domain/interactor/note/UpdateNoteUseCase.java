package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Completable;


public class UpdateNoteUseCase extends CompletableUseCase {

    private NoteRepository noteRepository;

    private NoteData note;

    @Inject
    public UpdateNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public UpdateNoteUseCase setParams(NoteData note) {
        this.note = note;
        return this;
    }

    @Override
    protected Completable  build() {
        return this.noteRepository.updateNote(this.note).ignoreElement();
    }
}