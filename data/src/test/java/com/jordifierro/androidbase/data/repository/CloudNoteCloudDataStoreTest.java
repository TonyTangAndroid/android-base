package com.jordifierro.androidbase.data.repository;

import com.google.common.truth.Truth;
import com.jordifierro.androidbase.domain.entity.NoteEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CloudNoteCloudDataStoreTest extends BaseDataRepositoryTest {

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
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        NoteEntity mock = Mockito.mock(NoteEntity.class);
        given(noteRemote.getNote(OBJECT_ID)).willReturn(Single.just(mock));
        this.noteCloudDataStore.getNoteEntity(OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.values()).isEqualTo(Collections.singletonList(mock));

        verify(noteDiskCacheImpl).put(mock);
        verifyNoMoreInteractions(noteDiskCacheImpl);
    }


}
