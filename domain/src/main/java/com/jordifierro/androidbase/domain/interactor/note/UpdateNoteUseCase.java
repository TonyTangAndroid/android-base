package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class UpdateNoteUseCase extends com.jordifierro.androidbase.domain.interactor.UseCase {

	private NoteRepository noteRepository;
	private SessionRepository sessionRepository;

	private NoteEntity note;

	@Inject
	public UpdateNoteUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
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
		return this.noteRepository.updateNote(currentUser, this.note).flatMap(new Func1<UpdatedWrapper, Observable<NoteEntity>>() {
			@Override
			public Observable<NoteEntity> call(UpdatedWrapper updatedWrapper) {
				return noteRepository.getNote(currentUser, note.getObjectId());
			}
		});
	}
}
