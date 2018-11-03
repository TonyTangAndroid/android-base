package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.CompletableUseCase;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Completable;


public class UpdateNoteUseCase extends CompletableUseCase {

    private NoteRepository noteRepository;

    private NoteEntity note;

    @Inject
    public UpdateNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public void setParams(NoteEntity note) {
        this.note = note;
    }

    @Override
    protected Completable build() {
        return this.noteRepository.updateNote(this.note);
    }
}
