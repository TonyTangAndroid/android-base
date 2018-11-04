package com.jordifierro.androidbase.data.repository;

import com.google.common.truth.Truth;
import com.jordifierro.androidbase.domain.entity.NoteEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NoteDataRepositoryTest extends BaseDataRepositoryTest {

    @Mock
    private NoteRemote noteRemote;
    @Mock
    private NoteDiskDataStore noteDiskDataStore;

    @Mock
    private NoteDataStoreFactory badgeDataStoreFactory;


    private NoteEntity fakeNote;
    private NoteDataRepository noteDataRepository;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        this.noteDataRepository = new NoteDataRepository(noteRemote, noteDiskDataStore, badgeDataStoreFactory);
        this.fakeNote = new NoteEntity(MOCK_NOTE_OBJECT_ID, MOCK_NOTE_TITLE, MOCK_NOTE_CONTENT);
    }


    @Test
    public void testGetNoteRequest() {

        NoteDataStore mockStore = mock(NoteDataStore.class);
        given(mockStore.getNoteEntity(MOCK_NOTE_OBJECT_ID)).willReturn(Single.just(fakeNote));
        given(badgeDataStoreFactory.getDataStore(MOCK_NOTE_OBJECT_ID)).willReturn(mockStore);
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        this.noteDataRepository.getNote(this.fakeNote.getObjectId()).subscribe(testObserver);

        Truth.assertThat(testObserver.values()).isEqualTo(Collections.singletonList(fakeNote));
        testObserver.assertComplete();

        verify(badgeDataStoreFactory).getDataStore(MOCK_NOTE_OBJECT_ID);
        verifyNoMoreInteractions(badgeDataStoreFactory);
        verify(mockStore).getNoteEntity(MOCK_NOTE_OBJECT_ID);
        verifyNoMoreInteractions(mockStore);
    }


    @Test
    public void testCreateNoteSuccessResponse() {

        TestObserver<String> testObserver = new TestObserver<>();

        given(noteRemote.createNote(fakeNote)).willReturn(Single.just(MOCK_NOTE_OBJECT_ID));

        this.noteDataRepository.createNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        Truth.assertThat(testObserver.values().get(0)).isEqualTo(MOCK_NOTE_OBJECT_ID);

        verify(noteRemote).createNote(fakeNote);
        verifyNoMoreInteractions(noteRemote);
    }


    @Test
    public void testUpdateNoteRequest() {

        TestObserver<String> testObserver = new TestObserver<>();

        given(noteRemote.updateNote(fakeNote)).willReturn(Completable.complete());

        this.noteDataRepository.updateNote(this.fakeNote).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoValues();

        verify(noteRemote).updateNote(fakeNote);
        verifyNoMoreInteractions(noteRemote);
        testObserver.assertComplete();
    }

    @Test
    public void testDeleteNoteSuccessResponse() {

        TestObserver<String> testObserver = new TestObserver<>();

        given(noteRemote.deleteNote(MOCK_NOTE_OBJECT_ID)).willReturn(Completable.complete());

        this.noteDataRepository.deleteNote(MOCK_NOTE_OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();


        testObserver.assertComplete();
        testObserver.assertNoValues();


        verify(noteRemote).deleteNote(MOCK_NOTE_OBJECT_ID);
        verifyNoMoreInteractions(noteRemote);
    }


}
