package com.tony.tang.movie;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

class MovieEntityPagingListPresenter {

    private final UI ui;
    private final DeleteMovieEntityUseCase deleteNoteUseCase;

    @Inject
    public MovieEntityPagingListPresenter(UI ui,
                                          DeleteMovieEntityUseCase deleteNoteUseCase) {
        this.ui = ui;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    void destroy() {
    }

    void delete(long id) {
        deleteNoteUseCase.setParams(id).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                ui.handleError(e);
            }
        });
    }

    public interface UI {

        void handleError(Throwable e);
    }


}
