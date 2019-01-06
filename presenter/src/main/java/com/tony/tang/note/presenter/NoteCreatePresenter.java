package com.tony.tang.note.presenter;

import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.interactor.note.CreateNoteUseCase;

import javax.inject.Inject;


public class NoteCreatePresenter extends BasePresenter implements Presenter {

    private CreateNoteUseCase createNoteUseCase;
    private NoteCreateView noteCreateView;


    @Inject
    public NoteCreatePresenter(NoteCreateView noteCreateView, CreateNoteUseCase createNoteUseCase) {
        super(noteCreateView, createNoteUseCase);
        this.noteCreateView = noteCreateView;
        this.createNoteUseCase = createNoteUseCase;
    }

    public void createButtonPressed(String title, String content) {
        NoteEntity note = NoteEntity.builder().title(title).content(content).build();
        this.noteCreateView.showLoader();
        this.createNoteUseCase.setParams(note);
        this.createNoteUseCase.execute(new NoteCreateSubscriber());
    }

    public interface NoteCreateView extends CleanView {
        void showLoader();

        void hideLoader();

        void exit(String objectId);
    }

    protected class NoteCreateSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onSuccess(NoteEntity note) {
            NoteCreatePresenter.this.noteCreateView.hideLoader();
            NoteCreatePresenter.this.noteCreateView.exit(note.objectId());
        }
    }
}
