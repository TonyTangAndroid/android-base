package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRemote;

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

public class DeleteUserUseCaseTest {

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private UIThread mockUIThread;
    @Mock
    private UserRemote mockUserRepository;
    @Mock
    private SessionRepository mockSessionRepository;
    @Mock
    private UserEntity mockUser;

    @Mock
    private VoidEntity voidEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteUserUseCaseSuccess() {
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(mockThreadExecutor,
                mockUIThread, mockUserRepository, mockSessionRepository);
        given(mockSessionRepository.getCurrentUser()).willReturn(mockUser);
        given(mockUserRepository.deleteUser(mockUser))
                .willReturn(Completable.complete());
        TestScheduler testScheduler = new TestScheduler();
        TestObserver<VoidEntity> testObserver = new TestObserver<>();
        deleteUserUseCase.build().observeOn(testScheduler).subscribe(testObserver);
        testScheduler.triggerActions();


        verify(mockUserRepository).deleteUser(mockUser);
        testObserver.assertComplete();
        verify(mockSessionRepository).getCurrentUser();
        verify(mockUserRepository).deleteUser(mockUser);
        verifyNoMoreInteractions(mockUserRepository);
        verify(mockSessionRepository).invalidateSession();
        verifyNoMoreInteractions(mockSessionRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}