package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UpdateNoteUseCaseTest {

    private static final int FAKE_ID = 1;
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
    @Mock
    private UserEntity mockUserEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateNoteUseCaseSuccess() {
        NoteEntity note = new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);
        UpdateNoteUseCase updateNoteUseCase = new UpdateNoteUseCase(mockThreadExecutor,
            mockPostExecutionThread, mockNoteRepository, mockSessionRepository);
        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        given(mockNoteRepository.updateNote(mockUserEntity, note)).willReturn(Observable.just(note));
        given(mockSessionRepository.getCurrentUser()).willReturn(mockUserEntity);

        updateNoteUseCase.setParams(note);
        updateNoteUseCase.buildUseCaseObservable().subscribe(testObserver);

        Assert.assertEquals(FAKE_TITLE,
            ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getTitle());
        Assert.assertEquals(FAKE_CONTENT,
            ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getContent());
        verify(mockSessionRepository).getCurrentUser();
        verifyNoMoreInteractions(mockSessionRepository);
        verify(mockNoteRepository).updateNote(mockUserEntity, note);
        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}