package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.TokenRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DeleteNoteUseCaseTest {

    private static final String FAKE_ID = "3WQrZ0dyrt";

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private NoteRepository mockNoteRepository;
    @Mock
    private TokenRepository mockSessionRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteNoteUseCaseSuccess() {
        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCase(mockThreadExecutor,
                mockUIThread, mockNoteRepository);

        deleteNoteUseCase.setParams(FAKE_ID);
        deleteNoteUseCase.build();

        verifyNoMoreInteractions(mockSessionRepository);
        verify(mockNoteRepository).deleteNote(FAKE_ID);
        verifyNoMoreInteractions(mockNoteRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}