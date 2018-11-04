package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.SingleUseCase;
import com.tony.tang.note.domain.repository.NoteRepository;

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
