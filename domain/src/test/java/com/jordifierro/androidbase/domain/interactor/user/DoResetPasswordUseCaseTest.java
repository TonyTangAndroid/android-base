package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DoResetPasswordUseCaseTest {

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserEntity mockUser;
    @Mock
    private VoidEntity voidEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoLogoutUseCaseSuccess() {
        ResetPasswordUseCase resetPasswordUseCase = new ResetPasswordUseCase(mockThreadExecutor,
                mockUIThread, mockUserRepository);
        resetPasswordUseCase.setParams(mockUser);


        given(mockUserRepository.resetPassword(mockUser.email()))
                .willReturn(Completable.complete());

        TestObserver<VoidEntity> testObserver = new TestObserver<>();
        TestScheduler testScheduler = new TestScheduler();
        resetPasswordUseCase.build().observeOn(testScheduler).subscribe(testObserver);
        testScheduler.triggerActions();


        verify(mockUserRepository).resetPassword(mockUser.email());
        testObserver.assertComplete();
        verifyNoMoreInteractions(mockUserRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}