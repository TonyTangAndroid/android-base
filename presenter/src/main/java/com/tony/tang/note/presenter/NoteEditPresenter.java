package com.tony.tang.note.presenter;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.interactor.note.GetNoteUseCase;
import com.tony.tang.note.domain.interactor.note.UpdateNoteUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;


public class NoteEditPresenter extends BasePresenter implements Presenter {

    private GetNoteUseCase getNoteUseCase;
    private UpdateNoteUseCase updateNoteUseCase;
    private NoteEditView noteEditView;


    @Inject
    public NoteEditPresenter(NoteEditView noteEditView, GetNoteUseCase getNoteUseCase, UpdateNoteUseCase updateNoteUseCase) {
        super(noteEditView, updateNoteUseCase);
        this.getNoteUseCase = getNoteUseCase;
        this.noteEditView = noteEditView;
        this.updateNoteUseCase = updateNoteUseCase;
    }

    @Override
    public void create() {
        super.create();
        loadData(noteEditView.objectId());
    }

    private void loadData(String objectId) {
        getNoteUseCase.setParams(objectId).execute(new BaseSubscriber<NoteEntity>() {
            @Override
            public void onSuccess(NoteEntity noteEntity) {
                noteEditView.bindData(noteEntity);
            }
        });
    }

    public void createButtonPressed(NoteData noteData) {
        this.noteEditView.showLoader();
        this.updateNoteUseCase.setParams(noteData);
        this.updateNoteUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                noteEditView.hideLoader();
                noteEditView.exit();
            }

            @Override
            public void onError(Throwable e) {
                noteEditView.handleError(e);
            }
        });
    }

    public interface NoteEditView extends CleanView {
        void showLoader();

        void hideLoader();

        void exit();

        String objectId();

        void bindData(NoteEntity noteEntity);
    }

}
