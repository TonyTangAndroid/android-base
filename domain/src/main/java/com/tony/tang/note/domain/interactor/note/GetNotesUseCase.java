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

import javax.inject.Inject;

import io.reactivex.Single;

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


    @Override
    protected Single<List<?>> build() {
        throw new RuntimeException();
    }


}
