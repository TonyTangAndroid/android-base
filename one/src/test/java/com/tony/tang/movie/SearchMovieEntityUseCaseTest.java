package com.tony.tang.movie;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class SearchMovieEntityUseCaseTest {

    private static final String KEYWORD_DARK_NIGHT = "dark_knight";
    private static final String KEYWORD_FARGO = "fargo";
    private static final String KEYWORD_GOOD_BAD_UGLY = "the_good_the_bad_and_the_ugly";
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private MovieEntityRepository mockNoteEntityListRepository;
    private PublishSubject<String> publishSubject;
    private TestObserver<List<MovieEntity>> testObserver;
    private TestScheduler testScheduler = new TestScheduler();

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        given(mockNoteEntityListRepository.list(KEYWORD_DARK_NIGHT)).willReturn(Single.just(list(KEYWORD_DARK_NIGHT)));
        given(mockNoteEntityListRepository.list(KEYWORD_FARGO)).willReturn(Single.just(list(KEYWORD_FARGO)));
        given(mockNoteEntityListRepository.list(KEYWORD_GOOD_BAD_UGLY)).willReturn(Single.just(list(KEYWORD_GOOD_BAD_UGLY)));

        testObserver = new TestObserver<>();
        publishSubject = PublishSubject.create();

        SearchMovieEntityUseCase searchNoteEntityUseCase = new SearchMovieEntityUseCase(mockThreadExecutor,
                mockUIThread, publishSubject, mockNoteEntityListRepository, testScheduler);
        testObserver = searchNoteEntityUseCase.build().subscribeWith(testObserver);


    }

    private List<MovieEntity> list(String keywordDarkNight) throws IOException {
        String json = TestUtils.json(keywordDarkNight + ".json", this);
        return new Gson().fromJson(json, new TypeToken<List<MovieEntity>>() {
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
        //when
        publishSubject.onComplete();
        testScheduler.triggerActions();

        testObserver.assertComplete();
        verifyNoMoreInteractions(mockNoteEntityListRepository);
    }

    private void emit(String keyword) throws IOException {
        //when
        publishSubject.onNext(keyword);
        testScheduler.triggerActions();

        //then
        testObserver.assertNoErrors();
        testObserver.assertNotComplete();
        testObserver.assertNoErrors();
        Truth.assertThat(testObserver.values()).contains(list(keyword));
        verify(mockNoteEntityListRepository).list(keyword);
        verifyNoMoreInteractions(mockNoteEntityListRepository);
    }
}