package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;
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
    private UserRepository mockUserRepository;
    @Mock
    private TokenRepository mockSessionRepository;

    @Mock
    private UserEntity userEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteUserUseCaseSuccess() {

        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(mockThreadExecutor,
                mockUIThread, mockUserRepository, mockSessionRepository);
        String MOCK_OBJECT_ID = "objectId";

        given(userEntity.objectId()).willReturn(MOCK_OBJECT_ID);
        given(mockUserRepository.getUserBySessionToken()).willReturn(Single.just(userEntity));
        given(mockSessionRepository.sessionToken()).willReturn(MOCK_OBJECT_ID);
        given(mockUserRepository.deleteUser(MOCK_OBJECT_ID)).willReturn(Completable.complete());
        TestScheduler testScheduler = new TestScheduler();
        TestObserver testObserver = new TestObserver<>();
        deleteUserUseCase.build().observeOn(testScheduler).subscribe(testObserver);
        testScheduler.triggerActions();


        verify(mockUserRepository).deleteUser(MOCK_OBJECT_ID);
        testObserver.assertComplete();
        verify(mockSessionRepository).invalidateSession();
        verify(mockUserRepository).deleteUser(MOCK_OBJECT_ID);
        verify(mockUserRepository).getUserBySessionToken();
        verifyNoMoreInteractions(mockUserRepository);
        verify(mockSessionRepository).invalidateSession();
        verifyNoMoreInteractions(mockSessionRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}