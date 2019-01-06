package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.SingleUseCase;
import com.tony.tang.note.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Single;


public class UpdateNoteUseCase extends SingleUseCase<NoteEntity> {

    private NoteRepository noteRepository;

    private NoteData note;

    @Inject
    public UpdateNoteUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             NoteRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public void setParams(NoteData note) {
        this.note = note;
    }

    @Override
    protected Single<NoteEntity> build() {
        return this.noteRepository.updateNote(this.note);
    }
}
