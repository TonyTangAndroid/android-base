package com.tony.tang.movie.ui.feature.list;

import com.tony.tang.movie.domain.DeleteMovieEntityUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

final class MovieEntityPagingPresenter {

    private final UI ui;
    private final DeleteMovieEntityUseCase deleteNoteUseCase;

    @Inject
    public MovieEntityPagingPresenter(UI ui,
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
