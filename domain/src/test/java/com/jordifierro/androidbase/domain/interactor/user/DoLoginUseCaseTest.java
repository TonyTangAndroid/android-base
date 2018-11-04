package com.jordifierro.androidbase.domain.interactor.user;

import com.google.common.truth.Truth;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRemote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DoLoginUseCaseTest {


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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoLoginUseCaseSuccess() {
        DoLoginUseCase doLoginUseCase = new DoLoginUseCase(mockThreadExecutor,
                mockUIThread, mockUserRepository, mockSessionRepository);
        TestObserver<UserEntity> testObserver = new TestObserver<>();
        given(mockUserRepository.loginUser(mockUser)).willReturn(Single.just(mockUser));

        doLoginUseCase.setParams(mockUser);
        testObserver = doLoginUseCase.build().subscribeWith(testObserver);

        verify(mockUserRepository).loginUser(mockUser);
        Truth.assertThat(testObserver.getEvents().get(0).get(0)).isEqualTo(mockUser);
        verifyNoMoreInteractions(mockUserRepository);
        verify(mockSessionRepository).setCurrentUser(mockUser);
        verifyNoMoreInteractions(mockSessionRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockUIThread);
    }
}