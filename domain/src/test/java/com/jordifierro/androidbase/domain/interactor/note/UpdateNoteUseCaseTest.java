package com.jordifierro.androidbase.domain.interactor.note;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
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

public class UpdateNoteUseCaseTest {

	public static final String FAKE_UPDATED_TIME = "2016-12-06T21:58:10.898Z";
	private static final String FAKE_ID = "3WQrZ0dyrt";
	private static final String FAKE_TITLE = "MyTitle";
	private static final String FAKE_CONTENT = "MyContent";
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
	public void testUpdateNoteUseCaseSuccess() {
		//XXX should return note object after updated.
		NoteEntity note = new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);
		UpdatedWrapper updatedWrapper = new UpdatedWrapper(FAKE_UPDATED_TIME);
		UpdateNoteUseCase updateNoteUseCase = new UpdateNoteUseCase(mockThreadExecutor,
				mockPostExecutionThread, mockNoteRepository, mockSessionRepository);
		TestSubscriber<UpdatedWrapper> testSubscriber = new TestSubscriber<>();
		given(mockNoteRepository.updateNote(any(UserEntity.class), eq(note)))
				.willReturn(Observable.just(updatedWrapper));

		updateNoteUseCase.setParams(note);
		updateNoteUseCase.buildUseCaseObservable().subscribe(testSubscriber);

		Assert.assertEquals(FAKE_UPDATED_TIME, testSubscriber.getOnNextEvents().get(0).getUpdatedAt());
		verify(mockSessionRepository).getCurrentUser();
		verifyNoMoreInteractions(mockSessionRepository);
		verify(mockNoteRepository).updateNote(null, note);
		verifyNoMoreInteractions(mockNoteRepository);
		verifyZeroInteractions(mockThreadExecutor);
		verifyZeroInteractions(mockPostExecutionThread);
	}
}