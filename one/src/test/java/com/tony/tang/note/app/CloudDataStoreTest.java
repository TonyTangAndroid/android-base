package com.tony.tang.note.app;

import com.google.common.truth.Truth;

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

public class CloudDataStoreTest {

    static final String KEYWORD = "fargo";

    NoteCloudDataStore noteCloudDataStore;

    @Mock
    private NoteRemote noteRemote;

    @Mock
    private NoteDiskCacheImpl noteDiskCacheImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.noteCloudDataStore = new NoteCloudDataStore(noteRemote, noteDiskCacheImpl);
    }

    @Test
    public void testGetNoteSuccessResponseCached() {
        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
        NoteEntity mock = Mockito.mock(NoteEntity.class);
        List<NoteEntity> list = Collections.singletonList(mock);
        given(noteRemote.list(KEYWORD)).willReturn(Single.just(list));
        this.noteCloudDataStore.list(KEYWORD).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.values().size()).isEqualTo(1);
        Truth.assertThat(testObserver.values().get(0)).isEqualTo(list);

        verify(noteDiskCacheImpl).put(KEYWORD, list);
        verifyNoMoreInteractions(noteDiskCacheImpl);
    }


}
