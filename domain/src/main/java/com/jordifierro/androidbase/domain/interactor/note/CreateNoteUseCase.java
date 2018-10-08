package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;

public class CreateNoteUseCase extends SingleUseCase<NoteEntity> {

    private NoteRepository noteRepository;

    private NoteEntity note;

    @Inject
    public CreateNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public CreateNoteUseCase setParams(NoteEntity note) {
        this.note = note;
        return this;
    }

    @Override
    protected Single<NoteEntity> build() {
        return this.noteRepository.createNote(this.note)
                .flatMap(this::toEntity);
    }

    private SingleSource<NoteEntity> toEntity(String objectId) {
        return noteRepository.getNote(objectId);
    }
}
