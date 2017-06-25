package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.CreateNoteUseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.NoteCreateView;

import javax.inject.Inject;


public class NoteCreatePresenter extends BasePresenter implements Presenter {

    private CreateNoteUseCase createNoteUseCase;
    NoteCreateView noteCreateView;


    @Inject
    public NoteCreatePresenter(CreateNoteUseCase createNoteUseCase) {
        super(createNoteUseCase);
        this.createNoteUseCase = createNoteUseCase;
    }

    @Override
    protected NoteCreateView getCleanView() {
        return noteCreateView;
    }

    @Override
    public void bindPresenter(CleanView view) {
        this.noteCreateView = (NoteCreateView) view;
    }

    @Override
    public void destroy() {
        super.destroy();
        this.noteCreateView = null;
    }

    public void createButtonPressed(String title, String content) {
        NoteEntity note = new NoteEntity(title, content);

        this.noteCreateView.showLoader();
        this.createNoteUseCase.setParams(note);
        this.createNoteUseCase.execute(new NoteCreateSubscriber());
    }

    protected class NoteCreateSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onNext(NoteEntity note) {
            NoteCreatePresenter.this.hideLoader();
            NoteCreatePresenter.this.noteCreateView.close();
        }
    }

}
