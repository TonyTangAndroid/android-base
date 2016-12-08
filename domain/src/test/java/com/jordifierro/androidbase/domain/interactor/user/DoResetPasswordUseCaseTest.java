package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DoResetPasswordUseCaseTest {

	@Mock
	private ThreadExecutor mockThreadExecutor;
	@Mock
	private PostExecutionThread mockPostExecutionThread;
	@Mock
	private UserRepository mockUserRepository;
	@Mock
	private UserEntity mockUser;
	@Mock
	private EmptyWrapper emptyWrapper;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDoLogoutUseCaseSuccess() {
		ResetPasswordUseCase resetPasswordUseCase = new ResetPasswordUseCase(mockThreadExecutor,
				mockPostExecutionThread, mockUserRepository);
		resetPasswordUseCase.setParams(mockUser);


		given(mockUserRepository.resetPassword(mockUser))
				.willReturn(Observable.just(emptyWrapper));

		TestSubscriber<EmptyWrapper> testSubscriber = new TestSubscriber<>();
		TestScheduler testScheduler = new TestScheduler();
		resetPasswordUseCase.buildUseCaseObservable().observeOn(testScheduler).subscribe(testSubscriber);
		testScheduler.triggerActions();


		verify(mockUserRepository).resetPassword(mockUser);
		Assert.assertEquals(emptyWrapper, testSubscriber.getOnNextEvents().get(0));

		verifyNoMoreInteractions(mockUserRepository);
		verifyZeroInteractions(mockThreadExecutor);
		verifyZeroInteractions(mockPostExecutionThread);
	}
}