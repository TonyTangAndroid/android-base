package com.tony.tang.movie;


import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteMovieEntityUseCase extends CompletableUseCase {

    private MovieEntityRepository noteRepository;

    private long id;

    @Inject
    public DeleteMovieEntityUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                                    MovieEntityRepository noteRepository) {
        super(threadExecutor, UIThread);
        this.noteRepository = noteRepository;
    }

    public DeleteMovieEntityUseCase setParams(long id) {
        this.id = id;
        return this;
    }

    @Override
    protected Completable build() {
        return this.noteRepository.delete(id);
    }
}
