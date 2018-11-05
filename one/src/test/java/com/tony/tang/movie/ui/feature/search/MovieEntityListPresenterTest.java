package com.tony.tang.movie.ui.feature.search;

import com.tony.tang.movie.domain.MovieEntity;
import com.tony.tang.movie.domain.SearchMovieEntityUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MovieEntityListPresenterTest {

    @Mock
    SearchMovieEntityUseCase getNoteUseCase;
    @Mock
    MovieEntitySearchPresenter.MovieListView mockMovieListView;

    private MovieEntitySearchPresenter noteDetailPresenter;
    private MovieEntitySearchPresenter.MovieListSubscriber noteDetailSubscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.noteDetailPresenter = new MovieEntitySearchPresenter(mockMovieListView, this.getNoteUseCase);
        this.noteDetailSubscriber = this.noteDetailPresenter.new MovieListSubscriber();
    }

    @Test
    public void testPause() {
        this.noteDetailPresenter.pause();
        verify(this.getNoteUseCase).unsubscribe();
        verifyNoMoreInteractions(this.getNoteUseCase);
    }


    @Test
    public void testGetNote() {
        this.noteDetailPresenter.resume();
        verify(this.getNoteUseCase).execute(any(MovieEntitySearchPresenter.MovieListSubscriber.class));
        verifyNoMoreInteractions(this.getNoteUseCase);
    }

    @Test
    public void testSubscriberOnError() {
        Throwable throwable = mock(Throwable.class);
        this.noteDetailSubscriber.onError(throwable);
        verify(this.mockMovieListView).handleError(throwable);
    }

    @Test
    public void testSubscriberOnNext() {
        List<MovieEntity> list = new ArrayList<>();
        this.noteDetailSubscriber.onNext(list);
        verify(this.mockMovieListView).bindData(list);
    }

}
