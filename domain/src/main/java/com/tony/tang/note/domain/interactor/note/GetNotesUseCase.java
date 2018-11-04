package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.constant.Constants;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.SingleUseCase;
import com.tony.tang.note.domain.repository.NoteListRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class GetNotesUseCase extends SingleUseCase<List<?>> {

    private NoteListRepository noteRepository;
    private Map<String, Object> queryParam = new HashMap<>();
    private List<NoteEntity> noteEntityList = new ArrayList<>();

    @Inject
    public GetNotesUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                           NoteListRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
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
    protected Single<List<?>> build() {
        return Single.timer(500, TimeUnit.MILLISECONDS).flatMap(new Function<Long, SingleSource<List<NoteEntity>>>() {
            @Override
            public SingleSource<List<NoteEntity>> apply(@NonNull Long aLong) throws Exception {
                return doGetNotes();
            }
        });
    }

    private Single<List<NoteEntity>> doGetNotes() {
        return this.noteRepository.getNotes(queryParam)
                .doOnSuccess(noteEntities -> updateNoteEntityCount(noteEntities.size())).map(noteEntities -> {
                    noteEntityList.addAll(noteEntities);
                    return noteEntityList;
                });
    }

    private void updateNoteEntityCount(int newNoteEntitySize) {
        queryParam.put(Constants.KEY_SKIP, (int) queryParam.get(Constants.KEY_SKIP) + newNoteEntitySize);
    }


}
