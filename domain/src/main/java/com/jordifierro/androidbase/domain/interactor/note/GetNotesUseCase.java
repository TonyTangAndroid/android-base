package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class GetNotesUseCase extends UseCase<List<NoteEntity>> {

    private NoteRepository noteRepository;
    private SessionRepository sessionRepository;

    @Inject
    public GetNotesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           NoteRepository noteRepository, SessionRepository sessionRepository) {
        super(threadExecutor, postExecutionThread);
        this.noteRepository = noteRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected Observable<List<NoteEntity>> buildUseCaseObservable() {
        return Observable.timer(500, TimeUnit.MILLISECONDS).flatMap(new Function<Long, ObservableSource<List<NoteEntity>>>() {
            @Override
            public ObservableSource<List<NoteEntity>> apply(@NonNull Long aLong) throws Exception {
                return doGetNotes();
            }
        });
    }

    private Observable<List<NoteEntity>> doGetNotes() {
        return this.noteRepository.getNotes(this.sessionRepository.getCurrentUser());
    }
}
