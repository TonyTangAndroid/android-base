package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class CreateNoteUseCase extends com.jordifierro.androidbase.domain.interactor.UseCase {

	private NoteRepository noteRepository;
	private SessionRepository sessionRepository;

	private NoteEntity note;

	@Inject
	public CreateNoteUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
							 NoteRepository noteRepository, SessionRepository sessionRepository) {
		super(threadExecutor, postExecutionThread);
		this.noteRepository = noteRepository;
		this.sessionRepository = sessionRepository;
	}

	public void setParams(NoteEntity note) {
		this.note = note;
	}

	@Override
	protected Observable<NoteEntity> buildUseCaseObservable() {
		final UserEntity currentUser = this.sessionRepository.getCurrentUser();
		return this.noteRepository.createNote(currentUser, this.note)
				.flatMap(new Func1<CreatedWrapper, Observable<NoteEntity>>() {
			@Override
			public Observable<NoteEntity> call(CreatedWrapper createdWrapper) {
				return noteRepository.getNote(currentUser, createdWrapper.getObjectId());
			}
		});
	}
}
