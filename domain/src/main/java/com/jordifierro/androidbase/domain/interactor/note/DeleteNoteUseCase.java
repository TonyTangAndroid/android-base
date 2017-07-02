package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.UserEntity;
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

    public DeleteNoteUseCase setParams(String noteObjectId) {
        this.noteObjectId = noteObjectId;
        return this;
    }

    @Override
    protected Observable<VoidEntity> buildUseCaseObservable() {
        UserEntity currentUser = this.sessionRepository.getCurrentUser();
        String s = sessionRepository.toString();
        return this.noteRepository.deleteNote(currentUser, this.noteObjectId);
    }
}
