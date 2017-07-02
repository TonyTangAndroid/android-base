package com.tony_tang.android.demo.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.exception.RestApiErrorException;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;
import com.tony_tang.android.demo.presentation.view.base.BaseListView;

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
    BaseListView mockNoteListView;
    @Mock
    Observable mockObservable;

    private NoteListPresenter noteListPresenter;
    private BaseListPresenter.BaseListSubscriber baseListSubscriber;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.noteListPresenter = new NoteListPresenter(mockNoteListView, this.getNotesUseCase, null, null);
        this.noteListPresenter.create();
        this.baseListSubscriber = this.noteListPresenter.new BaseListSubscriber();
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
        verify(this.getNotesUseCase).execute(any(BaseListPresenter.BaseListSubscriber.class));
    }

    @Test
    public void testSubscriberOnComplete() {

        this.baseListSubscriber.onComplete();

        verify(this.mockNoteListView).hideLoader();
    }

    @Test
    public void testSubscriberOnError() {

        this.baseListSubscriber.onError(new RestApiErrorException("Error message", 500));

        verify(this.mockNoteListView).hideLoader();
        verify(this.mockNoteListView).handleError(any(Throwable.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSubscriberOnNext() {

        this.baseListSubscriber.onNext(new ArrayList<NoteEntity>());

        verify(this.mockNoteListView).hideLoader();
        verify(this.mockNoteListView).showEntityList(any(List.class));
    }

}
