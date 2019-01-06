package com.tony.tang.note.presenter;

import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.exception.RestApiErrorException;
import com.tony.tang.note.domain.interactor.note.GetNoteUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class NoteDetailPresenterTest {

    private static final String MOCK_OBJECT_ID = "mock_object_id";
    @Mock
    GetNoteUseCase getNoteUseCase;
    @Mock
    NoteDetailPresenter.NoteDetailView mockNoteDetailView;

    private NoteDetailPresenter noteDetailPresenter;
    private NoteDetailPresenter.NoteDetailSubscriber noteDetailSubscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockNoteDetailView.getNoteObjectId()).willReturn(MOCK_OBJECT_ID);
        this.noteDetailPresenter = new NoteDetailPresenter(mockNoteDetailView, this.getNoteUseCase, updateNoteUseCase);
        this.noteDetailPresenter.create();
        this.noteDetailSubscriber = this.noteDetailPresenter.new NoteDetailSubscriber();
    }

    @Test
    public void testDestroy() {

        this.noteDetailPresenter.destroy();

        verify(this.getNoteUseCase).unsubscribe();
        assertNull(this.noteDetailPresenter.getCleanView());
    }

    @Test
    public void testGetNote() {

        this.noteDetailPresenter.resume();
        verify(this.mockNoteDetailView).getNoteObjectId();
        verify(this.mockNoteDetailView).showLoader();
        verify(this.getNoteUseCase).setParams(MOCK_OBJECT_ID);
        verify(this.getNoteUseCase).execute(any(NoteDetailPresenter.NoteDetailSubscriber.class));
    }

    @Test
    public void testSubscriberOnError() {

        this.noteDetailSubscriber.onError(new RestApiErrorException("Error message", 500));

        verify(this.mockNoteDetailView).hideLoader();
        verify(this.mockNoteDetailView).handleError(any(Throwable.class));
    }

    @Test
    public void testSubscriberOnNext() {

        this.noteDetailSubscriber.onSuccess(NoteEntity.builder().objectId("3WQrZ0dyrt").content("").title("").build());

        verify(this.mockNoteDetailView).hideLoader();
        verify(this.mockNoteDetailView).showNote(any(NoteEntity.class));
    }

}
