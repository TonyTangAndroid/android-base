package com.tony_tang.android.demo.feature.note_detail;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNoteUseCase;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class NoteDetailPresenter {

    GetNoteUseCase getNoteUseCase;
    NoteDetailView detailFragmentView;

    @DebugLog
    @Inject
    public NoteDetailPresenter(NoteDetailView detailFragmentView,
                               GetNoteUseCase getNoteUseCase) {
        this.detailFragmentView = detailFragmentView;
        this.getNoteUseCase = getNoteUseCase;

    }

    public void loadData() {

        this.getNoteUseCase.setParams(detailFragmentView.getNoteObjectId())
                .execute(new DisposableObserver<NoteEntity>() {
                    @Override
                    public void onNext(@NonNull NoteEntity noteEntityList) {
                        detailFragmentView.onNoteEntityLoaded(noteEntityList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
