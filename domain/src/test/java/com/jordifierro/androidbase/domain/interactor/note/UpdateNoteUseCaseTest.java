package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class UpdateNoteUseCaseTest {

    public static final String FAKE_UPDATED_TIME = "2016-12-06T21:58:10.898Z";
    private static final String FAKE_NEW_TITLE = "MyNewTitle";
    private static final String FAKE_NEW_CONTENT = "MyNewContent";

    private static final String FAKE_ID = "3WQrZ0dyrt";
    private static final String FAKE_TITLE = "MyTitle";
    private static final String FAKE_CONTENT = "MyContent";
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private NoteRepository mockNoteRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testUpdateNoteUseCaseSuccess() {

        NoteEntity note = new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);

        NoteEntity updatedNote = new NoteEntity(FAKE_ID, FAKE_NEW_TITLE, FAKE_NEW_CONTENT);
        updatedNote.setUpdatedAt(FAKE_UPDATED_TIME);


        given(mockNoteRepository.updateNote(any(NoteEntity.class))).willReturn(Completable.complete());
        given(mockNoteRepository.getNote(note.getObjectId())).willReturn(Single.just(updatedNote));


        UpdateNoteUseCase updateNoteUseCase = new UpdateNoteUseCase(mockThreadExecutor,
                mockUIThread, mockNoteRepository);
        updateNoteUseCase.setParams(note);
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        updateNoteUseCase.build().subscribe(testObserver);

        testObserver.assertComplete();
        verify(mockNoteRepository).updateNote(note);
        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }


}