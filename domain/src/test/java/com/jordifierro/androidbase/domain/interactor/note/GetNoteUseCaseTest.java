package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetNoteUseCaseTest {

    private static final String FAKE_ID = "3WQrZ0dyrt";

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
    public void testGetNoteUseCaseSuccess() {
//        GetNoteUseCase getNoteUseCase = new GetNoteUseCase(mockThreadExecutor,
//                mockPostExecutionThread, mockNoteRepository, mockSessionRepository);
//        TestObserver<NoteEntity> testObserver = new TestObserver<>();
//        NoteEntity note = new NoteEntity("Title", "Content");
//        given(mockNoteRepository.getNote(any(UserEntity.class), eq(FAKE_ID)))
//                .willReturn(Observable.just(note));
//
//        getNoteUseCase.setParams(FAKE_ID);
//        getNoteUseCase.buildUseCaseObservable().subscribe(testObserver);
//
//        Assert.assertEquals(note.getTitle(),
//                ((NoteEntity) (testObserver.getEvents().get(0)).get(0)).getTitle());
//        verify(mockSessionRepository).getCurrentUser();
//        verifyNoMoreInteractions(mockSessionRepository);
//        verify(mockNoteRepository).getNote(null, FAKE_ID);
//        verifyNoMoreInteractions(mockNoteRepository);
//        verifyZeroInteractions(mockThreadExecutor);
//        verifyZeroInteractions(mockPostExecutionThread);
    }
}