package com.jordifierro.androidbase.domain.interactor.note;

import com.google.common.truth.Truth;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CreateNoteUseCaseTest {

    private static final String FAKE_ID = "3WQrZ0dyrt";
    private static final String FAKE_TITLE = "MyTitle";
    private static final String FAKE_CONTENT = "MyContent";
    private static final String FAKE_CREATED = "12-06-2016";
    private static final String FAKE_SESSION = "sdajkfjwexssSdsdljlsdfweds";

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
    public void testCreateNoteUseCaseSuccess() {
        CreatedWrapper createdWrapper = new CreatedWrapper(FAKE_CREATED, FAKE_ID, FAKE_SESSION);
        NoteEntity note = new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);


        given(mockNoteRepository.createNote(any(NoteEntity.class))).willReturn(Single.just(FAKE_ID));
        given(mockNoteRepository.getNote(FAKE_ID)).willReturn(Single.just(note));


        TestObserver<NoteEntity> testObserver = new TestObserver<>();


        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCase(mockThreadExecutor,
                mockUIThread, mockNoteRepository);

        createNoteUseCase.setParams(note);
        testObserver = createNoteUseCase.build().subscribeWith(testObserver);
        Truth.assertThat(testObserver.getEvents().get(0).get(0)).isEqualTo(note);
        verify(mockNoteRepository).createNote(note);
        verify(mockNoteRepository).getNote(createdWrapper.getObjectId());

        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}