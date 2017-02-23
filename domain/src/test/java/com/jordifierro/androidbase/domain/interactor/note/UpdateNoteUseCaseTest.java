package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private NoteRepository mockNoteRepository;
    @Mock
    private SessionRepository mockSessionRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testUpdateNoteUseCaseSuccess() {

        UpdatedWrapper updatedWrapper = new UpdatedWrapper(FAKE_UPDATED_TIME);

        NoteEntity note = new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);

        NoteEntity updatedNote = new NoteEntity(FAKE_ID, FAKE_NEW_TITLE, FAKE_NEW_CONTENT);
        updatedNote.setUpdatedAt(FAKE_UPDATED_TIME);


        given(mockNoteRepository.updateNote(any(UserEntity.class), eq(note)))
                .willReturn(Observable.just(updatedWrapper));
        given(mockNoteRepository.getNote(any(UserEntity.class), eq(note.getObjectId())))
                .willReturn(Observable.just(updatedNote));


        UpdateNoteUseCase updateNoteUseCase = new UpdateNoteUseCase(mockThreadExecutor,
                mockPostExecutionThread, mockNoteRepository, mockSessionRepository);
        updateNoteUseCase.setParams(note);
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        updateNoteUseCase.buildUseCaseObservable().subscribe(testObserver);

        assertEquals(FAKE_UPDATED_TIME,
                ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getUpdatedAt());
        assertEquals(FAKE_NEW_CONTENT,
                ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getContent());

        assertEquals(FAKE_NEW_TITLE,
                ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getTitle());

        verify(mockSessionRepository).getCurrentUser();
        verifyNoMoreInteractions(mockSessionRepository);
        verify(mockNoteRepository).updateNote(null, note);
        verify(mockNoteRepository).getNote(null, note.getObjectId());
        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }


}