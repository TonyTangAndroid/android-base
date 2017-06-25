package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.exception.RestApiErrorException;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.view.NoteListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class NoteListPresenterTest {

    @Mock
    GetNotesUseCase getNotesUseCase;
    @Mock
    NoteListView mockNoteListView;
    @Mock
    Observable mockObservable;

    private NoteListPresenter noteListPresenter;
    private NoteListPresenter.NoteListSubscriber noteListSubscriber;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.noteListPresenter = new NoteListPresenter(mockNoteListView, this.getNotesUseCase);
        this.noteListPresenter.create();
        this.noteListSubscriber = this.noteListPresenter.new NoteListSubscriber();
    }

    @Test
    public void testDestroy() {

        this.noteListPresenter.destroy();

        verify(this.getNotesUseCase).unsubscribe();
        assertNull(this.noteListPresenter.getCleanView());
    }

    @Test
    public void testGetNotes() throws Exception {

        this.noteListPresenter.resume();

        verify(this.mockNoteListView).showLoader();
        verify(this.getNotesUseCase).execute(any(NoteListPresenter.NoteListSubscriber.class));
    }

    @Test
    public void testSubscriberOnComplete() {

        this.noteListSubscriber.onComplete();

        verify(this.mockNoteListView).hideLoader();
    }

    @Test
    public void testSubscriberOnError() {

        this.noteListSubscriber.onError(new RestApiErrorException("Error message", 500));

        verify(this.mockNoteListView).hideLoader();
        verify(this.mockNoteListView).handleError(any(Throwable.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSubscriberOnNext() {

        this.noteListSubscriber.onNext(new ArrayList<NoteEntity>());

        verify(this.mockNoteListView).hideLoader();
        verify(this.mockNoteListView).showNoteList(any(List.class));
    }

}
