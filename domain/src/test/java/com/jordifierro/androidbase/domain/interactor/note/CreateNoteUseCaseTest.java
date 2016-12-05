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

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CreateNoteUseCaseTest {

    private static final String FAKE_ID = "BLlerFMbhA";
    private static final String FAKE_TITLE = "MyTitle";
    private static final String FAKE_CONTENT = "MyContent";

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private NoteRepository mockNoteRepository;
    @Mock private SessionRepository mockSessionRepository;

    @Before
    public void setup() { MockitoAnnotations.initMocks(this); }

    @Test
    public void testCreateNoteUseCaseSuccess() {
        NoteEntity note = new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCase(mockThreadExecutor,
                mockPostExecutionThread, mockNoteRepository, mockSessionRepository);
        given(mockNoteRepository.createNote(any(UserEntity.class), eq(note)))
                .willReturn(Observable.just(note));
        TestSubscriber<NoteEntity> testSubscriber = new TestSubscriber<>();

        createNoteUseCase.setParams(note);
        createNoteUseCase.buildUseCaseObservable().subscribe(testSubscriber);

        Assert.assertEquals(FAKE_TITLE, testSubscriber.getOnNextEvents().get(0).getTitle());
        Assert.assertEquals(FAKE_CONTENT, testSubscriber.getOnNextEvents().get(0).getContent());
        verify(mockSessionRepository).getCurrentUser();
        verifyNoMoreInteractions(mockSessionRepository);
        verify(mockNoteRepository).createNote(null, note);
        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}