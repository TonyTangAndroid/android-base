package com.tony.tang.note.app;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SearchMovieUseCase extends UseCase<List<NoteEntity>> {

    private final Observable<String> keywordStreamObservable;
    private final NoteRepository movieListRepository;

    @Inject
    public SearchMovieUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                              Observable<String> keywordStreamObservable,
                              NoteRepository movieListRepository) {
        super(threadExecutor, UIThread);
        this.movieListRepository = movieListRepository;
        this.keywordStreamObservable = keywordStreamObservable;
    }


    @Override
    protected Observable<List<NoteEntity>> build() {
        return keywordStreamObservable.flatMap(this::search);
    }

    private Observable<List<NoteEntity>> search(String keyword) {
        return this.movieListRepository.list(keyword).toObservable();

    }

}
