package com.tony_tang.android.demo.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNoteUseCase;
import com.tony_tang.android.demo.presentation.presenter.base.BasePresenter;
import com.tony_tang.android.demo.presentation.presenter.base.Presenter;
import com.tony_tang.android.demo.presentation.view.NoteDetailView;

import javax.inject.Inject;


public class NoteDetailPresenter extends BasePresenter implements Presenter {

    private NoteDetailView noteDetailView;
    private GetNoteUseCase getNoteUseCase;


    //重点六： Presenter 注入过程：
    //Q1, NoteDetailView 是如何被提供的？
    //A1: 1.NoteDetailView被NoteDetailFragment实现了。
    //    2, NoteDetailFragment在NoteDetailModule绑定了。
          //3,NoteDetailModule在 DemoFragmentInjector里绑定了
    //4, DemoFragmentInjector 在终极Boss DemoApplicationComponent 注册了。

    //Q2: GetNoteUseCase如何被提供的？
    //A2:  GetNoteUseCase可以为所欲为地使用任何Application下所有的依赖。
    @Inject
    public NoteDetailPresenter(NoteDetailView noteDetailView,
                               GetNoteUseCase getNoteUseCase) {
        super(noteDetailView, getNoteUseCase);
        this.noteDetailView = noteDetailView;
        this.getNoteUseCase = getNoteUseCase;
    }

    @Override
    public void resume() {
        this.showLoader();
        this.getNoteUseCase.setParams(this.noteDetailView.getNoteObjectId());
        this.getNoteUseCase.execute(new NoteDetailSubscriber());
    }

    protected class NoteDetailSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            NoteDetailPresenter.this.noteDetailView.close();
        }

        @Override
        public void onSuccess(NoteEntity note) {
            NoteDetailPresenter.this.hideLoader();
            NoteDetailPresenter.this.noteDetailView.showNote(note);
        }
    }

}
