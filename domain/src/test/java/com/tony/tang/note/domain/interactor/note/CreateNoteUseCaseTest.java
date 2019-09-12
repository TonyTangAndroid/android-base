package com.tony.tang.note.domain.interactor.note;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.repository.NoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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
    private NoteEntity noteEntity;
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
        NoteData note = mock(NoteData.class);
        given(mockNoteRepository.createNote(note)).willReturn(Single.just(noteEntity));
        given(mockNoteRepository.getNote(FAKE_ID)).willReturn(Single.just(noteEntity));

        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCase(mockThreadExecutor,
                mockUIThread, mockNoteRepository);
        createNoteUseCase.setParams(note);
        createNoteUseCase.build().test().assertValue(noteEntity);
        verify(mockNoteRepository).createNote(note);
        verifyNoMoreInteractions(mockNoteRepository);

        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}