package com.tony.tang.note.ui.feature.note.list;

import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.domain.constant.Constants;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.repository.NoteListRepository;
import com.tony.tang.note.remote.ParseDate;
import com.tony.tang.note.remote.ParseOperator;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import dagger.Reusable;

@Reusable
public class NoteBeanBoundaryCallback extends PagedList.BoundaryCallback<NoteBean> {
    private final DateFormat dateFormat;
    private final NoteListRepository repository;
    private final ThreadExecutor threadExecutor;

    @Inject
    public NoteBeanBoundaryCallback(DateFormat dateFormat,
                                    NoteListRepository repository,
                                    ThreadExecutor threadExecutor) {
        this.dateFormat = dateFormat;
        this.repository = repository;
        this.threadExecutor = threadExecutor;
    }

    @Override
    public void onZeroItemsLoaded() {
        fetch(null);
    }

    private void fetch(Date createdAt) {
        threadExecutor.execute(() -> repository.getNotes(param(createdAt)).ignoreElement().blockingAwait());
    }

    private Map<String, Object> param(@Nullable Date createdAt) {
        if (createdAt != null) {
            Map<String, Object> queryParam = new HashMap<>();
            queryParam.put(Constants.KEY_CREATED_AT, query(createdAt));
            return queryParam;
        } else {
            return null;
        }
    }

    private ParseOperator query(@NonNull Date createdAt) {
        String formattedDate = dateFormat.format(createdAt);
        ParseDate parseDate = ParseDate.builder().iso(formattedDate).build();
        return ParseOperator.builder().greatThan(parseDate).build();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull NoteBean itemAtFront) {
    }

    @Override
    public void onItemAtEndLoaded(@NonNull NoteBean itemAtEnd) {
        fetch(itemAtEnd.createdAt);
    }

}