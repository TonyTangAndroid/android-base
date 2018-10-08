package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetNoteUseCase extends SingleUseCase<NoteEntity> {

    private NoteRepository noteRepository;

    private String noteObjectId;

    @Inject
    public GetNoteUseCase(ThreadExecutor threadExecutor,
                          UIThread UIThread,
                          NoteRepository noteRepository) {

        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public GetNoteUseCase setParams(String noteObjectId) {
        this.noteObjectId = noteObjectId;
        return this;
    }

    @Override
    protected Single<NoteEntity> build() {
        return this.noteRepository.getNote(this.noteObjectId);
    }
}
