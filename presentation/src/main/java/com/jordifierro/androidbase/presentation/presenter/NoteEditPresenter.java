package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.interactor.note.DeleteNoteUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GetNoteUseCase;
import com.jordifierro.androidbase.domain.interactor.note.UpdateNoteUseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.NoteEditView;

import javax.inject.Inject;


public class NoteEditPresenter extends BasePresenter implements Presenter {

    private UpdateNoteUseCase updateNoteUseCase;
    private GetNoteUseCase getNoteUseCase;
    private DeleteNoteUseCase deleteNoteUseCase;
    private NoteEditView noteEditView;


    @Inject
    public NoteEditPresenter(UpdateNoteUseCase updateNoteUseCase,
                             GetNoteUseCase getNoteUseCase, DeleteNoteUseCase deleteNoteUseCase) {
        super(updateNoteUseCase, getNoteUseCase, deleteNoteUseCase);
        this.updateNoteUseCase = updateNoteUseCase;
        this.getNoteUseCase = getNoteUseCase;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    @Override
    protected NoteEditView getCleanView() {
        return noteEditView;
    }

    @Override
    public void bindPresenter(CleanView view) {
        this.noteEditView = (NoteEditView) view;

        this.showLoader();
        this.getNoteUseCase.setParams(this.noteEditView.getNoteObjectId());
        this.getNoteUseCase.execute(new GetNoteSubscriber());
    }

    @Override
    public void destroy() {
        super.destroy();
        this.noteEditView = null;
    }

    public void updateNote(String title, String content) {
        NoteEntity updatedNote = new NoteEntity(title, content);
        updatedNote.setObjectId(this.noteEditView.getNoteObjectId());

        this.noteEditView.showLoader();
        this.updateNoteUseCase.setParams(updatedNote);
        this.updateNoteUseCase.execute(new UpdateNoteSubscriber());
    }

    public void deleteNoteButtonPressed() {
        this.noteEditView.showLoader();
        this.deleteNoteUseCase.setParams(this.noteEditView.getNoteObjectId());
        this.deleteNoteUseCase.execute(new DeleteNoteSubscriber());
    }

    protected class GetNoteSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onNext(NoteEntity note) {
            NoteEditPresenter.this.hideLoader();
            NoteEditPresenter.this.noteEditView.showNote(note);
        }
    }

    protected class UpdateNoteSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onNext(NoteEntity note) {
            NoteEditPresenter.this.hideLoader();
            NoteEditPresenter.this.noteEditView.close();
        }

    }

    protected class DeleteNoteSubscriber extends BaseSubscriber<VoidEntity> {

        @Override
        public void onNext(VoidEntity ignore) {
            NoteEditPresenter.this.hideLoader();
            NoteEditPresenter.this.noteEditView.onNoteDeleted();
            NoteEditPresenter.this.noteEditView.close();
        }

    }

}
