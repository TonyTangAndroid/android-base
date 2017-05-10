package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.view.BaseView;
import com.jordifierro.androidbase.presentation.view.NotesViewPagerView;

import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public class NotesViewPagerPresenter extends BasePresenter implements Presenter {

    NotesViewPagerView notesView;
    private GetNotesUseCase getNotesUseCase;

    @Inject
    //@DebugLog
    public NotesViewPagerPresenter(GetNotesUseCase getNotesUseCase) {
        super(getNotesUseCase);
        this.getNotesUseCase = getNotesUseCase;
    }

    @Override
    public void initWithView(BaseView view) {
        super.initWithView(view);
        this.notesView = (NotesViewPagerView) view;
        this.notesView.initView();
    }

    @Override
    public void resume() {
        this.showLoader();
        this.getNotesUseCase.execute(new NotesSubscriber());
    }

    @Override
    public void destroy() {
        super.destroy();
        this.notesView = null;
    }

    protected class NotesSubscriber extends BaseSubscriber<List<NoteEntity>> {

        @Override
        public void onNext(List<NoteEntity> notes) {
            NotesViewPagerPresenter.this.hideLoader();
            NotesViewPagerPresenter.this.notesView.showNotes(notes);
        }
    }


}
