package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.constant.Constants;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class GetNotesUseCase extends UseCase<List<NoteEntity>> {

    private NoteRepository noteRepository;
    private SessionRepository sessionRepository;
    private Map<String, Object> queryParam = new HashMap<>();
    private List<NoteEntity> noteEntityList = new ArrayList<>();

    @Inject
    public GetNotesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           NoteRepository noteRepository, SessionRepository sessionRepository) {
        super(threadExecutor, postExecutionThread);
        this.noteRepository = noteRepository;
        this.sessionRepository = sessionRepository;
        initQueryParam();
    }

    private void initQueryParam() {
        queryParam.put(Constants.KEY_LIMIT, Constants.DEFAULT_LIMIT_VALUE);
        queryParam.put(Constants.KEY_SKIP, Constants.DEFAULT_SKIP_VALUE);
        noteEntityList.clear();

    }

    public GetNotesUseCase resetQueryParam() {
        initQueryParam();
        return this;
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
        return this.noteRepository.getNotes(this.sessionRepository.getCurrentUser(), queryParam).doOnNext(new Consumer<List<NoteEntity>>() {
            @Override
            public void accept(@NonNull List<NoteEntity> noteEntities) throws Exception {
                updateNoteEntityCount(noteEntities.size());
            }
        }).map(new Function<List<NoteEntity>, List<NoteEntity>>() {
            @Override
            public List<NoteEntity> apply(@NonNull List<NoteEntity> noteEntities) throws Exception {
                noteEntityList.addAll(noteEntities);
                return noteEntityList;
            }
        });
    }

    private void updateNoteEntityCount(int newNoteEntitySize) {
        queryParam.put(Constants.KEY_SKIP, (int) queryParam.get(Constants.KEY_SKIP) + newNoteEntitySize);
    }


}
