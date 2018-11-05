package com.tony.tang.note.domain.interactor.note;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tony.tang.note.domain.entity.Movie;
import com.tony.tang.note.domain.entity.TestUtils;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.repository.MovieRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class SearchMovieUseCaseTest {

    private static final String KEYWORD_DARK_NIGHT = "dark_knight";
    private static final String KEYWORD_FARGO = "fargo";
    private static final String KEYWORD_GOOD_BAD_UGLY = "the_good_the_bad_and_the_ugly";
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private MovieRepository mockMovieListRepository;
    private PublishSubject<String> publishSubject;
    private TestObserver<List<Movie>> testObserver;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        given(mockMovieListRepository.list(KEYWORD_DARK_NIGHT)).willReturn(Single.just(list(KEYWORD_DARK_NIGHT)));
        given(mockMovieListRepository.list(KEYWORD_FARGO)).willReturn(Single.just(list(KEYWORD_FARGO)));
        given(mockMovieListRepository.list(KEYWORD_GOOD_BAD_UGLY)).willReturn(Single.just(list(KEYWORD_GOOD_BAD_UGLY)));

        testObserver = new TestObserver<>();
        publishSubject = PublishSubject.create();

        SearchMovieUseCase searchMovieUseCase = new SearchMovieUseCase(mockThreadExecutor,
                mockUIThread, publishSubject, mockMovieListRepository);
        testObserver = searchMovieUseCase.build().subscribeWith(testObserver);


    }

    private List<Movie> list(String keywordDarkNight) throws IOException {
        String json = TestUtils.json(keywordDarkNight + ".json", this);
        return new Gson().fromJson(json, new TypeToken<List<Movie>>() {
        }.getType());
    }

    @Test
    public void testCreateNoteUseCaseSuccess() throws IOException {
        emit(KEYWORD_DARK_NIGHT);
        emit(KEYWORD_FARGO);
        emit(KEYWORD_GOOD_BAD_UGLY);
        complete();

    }

    private void complete() {
        publishSubject.onComplete();
        testObserver.assertComplete();
        verifyNoMoreInteractions(mockMovieListRepository);
    }

    private void emit(String keyword) throws IOException {
        //when
        publishSubject.onNext(keyword);
        //then
        testObserver.assertNoErrors();
        testObserver.assertNotComplete();
        Truth.assertThat(testObserver.values()).contains(list(keyword));
        verify(mockMovieListRepository).list(keyword);
        verifyNoMoreInteractions(mockMovieListRepository);
    }
}