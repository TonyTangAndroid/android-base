package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


public class UpdateNoteUseCase extends com.jordifierro.androidbase.domain.interactor.UseCase {

    private NoteRepository noteRepository;
    private SessionRepository sessionRepository;

    private NoteEntity note;

    @Inject
    public UpdateNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository, SessionRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
        this.sessionRepository = sessionRepository;
    }

    public void setParams(NoteEntity note) {
        this.note = note;
    }

    @Override
    protected Observable<NoteEntity> build() {
        throw new RuntimeException();
    }
}
