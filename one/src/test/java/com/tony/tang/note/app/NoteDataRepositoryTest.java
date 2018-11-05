package com.tony.tang.note.app;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NoteDataRepositoryTest {

    static final String KEYWORD = "fargo";
    static final int ID = 1;


    @Mock
    private NoteRemote noteRemote;
    @Mock
    private NoteDiskCacheImpl noteDiskCacheImpl;

    @Mock
    private NoteDataStoreFactory badgeDataStoreFactory;


    @Mock
    private NoteEntity fakeNote;
    private NoteDataRepository noteDataRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.noteDataRepository = new NoteDataRepository(badgeDataStoreFactory, noteDiskCacheImpl);
    }


    @Test
    public void list() {

        List<NoteEntity> list = Collections.singletonList(fakeNote);
        NoteDataStore mockStore = mock(NoteDataStore.class);
        given(mockStore.list(KEYWORD)).willReturn(Single.just(list));
        given(badgeDataStoreFactory.getDataStore(KEYWORD)).willReturn(mockStore);
        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
        this.noteDataRepository.list(KEYWORD).subscribe(testObserver);

        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
        Truth.assertThat(testObserver.values().get(0)).isEqualTo(list);
        testObserver.assertComplete();

        verify(badgeDataStoreFactory).getDataStore(KEYWORD);
        verifyNoMoreInteractions(badgeDataStoreFactory);
        verify(mockStore).list(KEYWORD);
        verifyNoMoreInteractions(mockStore);
    }


    @Test
    public void testDeleteNoteSuccessResponse() {

        TestObserver<String> testObserver = new TestObserver<>();

        given(noteDiskCacheImpl.delete(ID)).willReturn(Completable.complete());

        this.noteDataRepository.delete(ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();


        testObserver.assertComplete();
        testObserver.assertNoValues();
        
        verify(noteDiskCacheImpl).delete(ID);
        verifyNoMoreInteractions(noteDiskCacheImpl);
    }
}
