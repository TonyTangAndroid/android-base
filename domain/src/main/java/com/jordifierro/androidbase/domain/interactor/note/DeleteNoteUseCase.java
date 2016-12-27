package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DeleteNoteUseCase extends UseCase<VoidEntity> {

    private NoteRepository noteRepository;
    private SessionRepository sessionRepository;

    private String noteObjectId;

    @Inject
    public DeleteNoteUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                             NoteRepository noteRepository, SessionRepository sessionRepository) {
        super(threadExecutor, postExecutionThread);
        this.noteRepository = noteRepository;
        this.sessionRepository = sessionRepository;
    }

    public void setParams(String noteObjectId) {
        this.noteObjectId = noteObjectId;
    }

    @Override
    protected Observable<VoidEntity> buildUseCaseObservable() {
        return this.noteRepository.deleteNote(this.sessionRepository.getCurrentUser(), this.noteObjectId);
    }
}
