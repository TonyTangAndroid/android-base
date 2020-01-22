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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetNoteUseCaseTest {

    private static final int FAKE_ID = 1;

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
    public void testGetNoteUseCaseSuccess() {
        GetNoteUseCase getNoteUseCase = new GetNoteUseCase(mockThreadExecutor,
            mockPostExecutionThread, mockNoteRepository, mockSessionRepository);

        TestObserver<NoteEntity> testObserver = new TestObserver<>();
        NoteEntity note = new NoteEntity("Title", "Content");
        given(mockNoteRepository.getNote(mockUserEntity, FAKE_ID)).willReturn(Observable.just(note));
        given(mockSessionRepository.getCurrentUser()).willReturn(mockUserEntity);

        getNoteUseCase.setParams(FAKE_ID);
        getNoteUseCase.buildUseCaseObservable().subscribe(testObserver);

        Assert.assertEquals(note.getTitle(),
            ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getTitle());
        verify(mockSessionRepository).getCurrentUser();
        verifyNoMoreInteractions(mockSessionRepository);
        verify(mockNoteRepository).getNote(mockUserEntity, FAKE_ID);
        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}