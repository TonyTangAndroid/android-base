package com.tony_tang.android.demo.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.CreateNoteUseCase;
import com.tony_tang.android.demo.presentation.presenter.base.BasePresenter;
import com.tony_tang.android.demo.presentation.presenter.base.Presenter;
import com.tony_tang.android.demo.presentation.view.NoteCreateView;

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
        NoteEntity note = new NoteEntity(title, content);
        this.noteCreateView.showLoader();
        this.createNoteUseCase.setParams(note);
        this.createNoteUseCase.execute(new NoteCreateSubscriber());
    }

    protected class NoteCreateSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onSuccess(NoteEntity note) {
            NoteCreatePresenter.this.hideLoader();
            NoteCreatePresenter.this.noteCreateView.close();
        }
    }

}
