package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetNotesUseCaseTest {

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private NoteRepository mockNoteRepository;
    @Mock
    private SessionRepository mockSessionRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNotesUseCaseSuccess() {
//        GetNotesUseCase getNotesUseCase = new GetNotesUseCase(mockThreadExecutor,
//                mockPostExecutionThread, mockNoteRepository, mockSessionRepository);
//        TestObserver<List<NoteEntity>> testObserver = new TestObserver<>();
//        List<NoteEntity> notes =
//                Arrays.asList(new NoteEntity("t1", "c1"), new NoteEntity("t2", "c2"));
//        given(mockNoteRepository.getNotes(any(UserEntity.class), null))
//                .willReturn(Observable.just(notes));
//
//        getNotesUseCase.buildUseCaseObservable().subscribe(testObserver);
//
//        Assert.Truth.assertThat(notes.size(),
//                ((List<NoteEntity>) (testObserver.getEvents().get(0)).get(0)).size());
//        Assert.Truth.assertThat(notes.get(1).content(),
//                ((List<NoteEntity>) (testObserver.getEvents().get(0)).get(0)).get(1).content());
//        verify(mockSessionRepository).getCurrentUser();
//        verifyNoMoreInteractions(mockSessionRepository);
//        verify(mockNoteRepository).getNotes(null, null);
//        verifyNoMoreInteractions(mockNoteRepository);
//        verifyZeroInteractions(mockThreadExecutor);
//        verifyZeroInteractions(mockPostExecutionThread);
    }
}