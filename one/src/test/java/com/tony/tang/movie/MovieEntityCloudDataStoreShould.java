package com.tony.tang.movie;

import com.google.common.truth.Truth;
import com.tony.tang.movie.MovieEntity;
import com.tony.tang.movie.MovieEntityCloudDataStore;
import com.tony.tang.movie.MovieEntityDiskCacheImpl;
import com.tony.tang.movie.MovieEntityRemote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MovieEntityCloudDataStoreShould {

    static final String KEYWORD = "fargo";

    MovieEntityCloudDataStore noteCloudDataStore;

    @Mock
    private MovieEntityRemote noteRemote;

    @Mock
    private MovieEntityDiskCacheImpl noteDiskCacheImpl;
    private TestObserver<List<MovieEntity>> testObserver;
    private List<MovieEntity> list;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.noteCloudDataStore = new MovieEntityCloudDataStore(noteRemote, noteDiskCacheImpl);
        testObserver = new TestObserver<>();


        //given
        MovieEntity mock = Mockito.mock(MovieEntity.class);
        list = Collections.singletonList(mock);
        given(noteRemote.list(KEYWORD)).willReturn(Single.just(list));

        //when
        this.noteCloudDataStore.list(KEYWORD).subscribe(testObserver);
        testObserver.awaitTerminalEvent();


    }

    @Test
    public void listMovieEntitySuccessAndCacheResponse() {
        //then
        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
        Truth.assertThat(testObserver.values().get(0)).isEqualTo(list);

    }


    @Test
    public void cacheMovieEntityListDataSuccess() {
        //then
        verify(noteDiskCacheImpl).put(KEYWORD, list);
        verifyNoMoreInteractions(noteDiskCacheImpl);
    }


}
