package com.tony.tang.note.data;

import com.google.common.truth.Truth;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NoteDataRepositoryTest {

    static final String OBJECT_ID = "3WQrZ0dyrt";


    @Mock
    private NoteRemote noteRemote;
    @Mock
    private NoteDiskCacheImpl noteDiskCache;


    @Mock
    private NoteEntity noteEntity;

    @Mock
    private NoteData noteData;

    private NoteDataRepository noteDataRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.noteDataRepository = new NoteDataRepository(noteRemote, noteEntityDao, noteDiskCache);
    }


    @Test
    public void testGetNoteRequest() {

        given(noteRemote.getNote(OBJECT_ID)).willReturn(Single.just(noteEntity));
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        this.noteDataRepository.getNote(OBJECT_ID).subscribe(testObserver);

        Truth.assertThat(testObserver.values()).isEqualTo(Collections.singletonList(noteEntity));
        testObserver.assertComplete();

        verify(noteRemote).getNote(OBJECT_ID);
        verifyNoMoreInteractions(noteRemote);


        verify(noteDiskCache).put(noteEntity);
        verifyNoMoreInteractions(noteDiskCache);
    }


    @Test
    public void testCreateNoteSuccessResponse() {

        TestObserver<NoteEntity> testObserver = new TestObserver<>();

        given(noteRemote.createNote(noteData)).willReturn(Single.just(OBJECT_ID));
        given(noteRemote.getNote(OBJECT_ID)).willReturn(Single.just(noteEntity));

        this.noteDataRepository.createNote(this.noteData).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        Truth.assertThat(testObserver.values().get(0)).isEqualTo(noteEntity);

        verify(noteRemote).createNote(noteData);
        verify(noteRemote).getNote(OBJECT_ID);


        verifyNoMoreInteractions(noteRemote);


        verify(noteDiskCache).put(noteEntity);
        verifyNoMoreInteractions(noteDiskCache);
    }


    @Test
    public void testUpdateNoteRequest() {

        TestObserver<NoteEntity> testObserver = new TestObserver<>();

        given(noteData.objectId()).willReturn(OBJECT_ID);
        given(noteRemote.updateNote(noteData)).willReturn(Completable.complete());
        given(noteRemote.getNote(OBJECT_ID)).willReturn(Single.just(noteEntity));

        this.noteDataRepository.updateNote(this.noteData).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertValue(noteEntity);

        verify(noteRemote).updateNote(noteData);
        verify(noteRemote).getNote(OBJECT_ID);
        verifyNoMoreInteractions(noteRemote);


        verify(noteDiskCache).put(noteEntity);
        verifyNoMoreInteractions(noteDiskCache);
    }

    @Test
    public void testDeleteNoteSuccessResponse() {

        TestObserver<String> testObserver = new TestObserver<>();

        given(noteRemote.deleteNote(OBJECT_ID)).willReturn(Completable.complete());

        this.noteDataRepository.deleteNote(OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();


        testObserver.assertComplete();
        testObserver.assertNoValues();


        verify(noteRemote).deleteNote(OBJECT_ID);
        verifyNoMoreInteractions(noteRemote);

        verify(noteDiskCache).delete(OBJECT_ID);
        verifyNoMoreInteractions(noteDiskCache);

    }
}
