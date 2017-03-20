package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CreateUserUseCaseTest {

    private static final String FAKE_PASS = "1234";
    private static final String FAKE_SESSION_TOKEN = "fake_auth_token";

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private SessionRepository mockSessionRepository;
    @Mock
    private UserEntity mockUser;
    @Mock
    private CreatedWrapper createdWrapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateUserUseCaseSuccess() {

        given(createdWrapper.getSessionToken()).willReturn(FAKE_SESSION_TOKEN);


        CreateUserUseCase createUserUseCase = new CreateUserUseCase(mockThreadExecutor,
                mockPostExecutionThread, mockUserRepository, mockSessionRepository);
        TestObserver<UserEntity> testObserver = new TestObserver<>();
        given(mockUserRepository.createUser(mockUser)).willReturn(Observable.just(createdWrapper));
        given(mockUserRepository.getUserBySessionToken(FAKE_SESSION_TOKEN)).willReturn(Observable.just(mockUser));

        createUserUseCase.setParams(mockUser);
        createUserUseCase.buildUseCaseObservable().subscribeWith(testObserver);

        verify(mockUserRepository).createUser(mockUser);
        verify(mockUserRepository).getUserBySessionToken(FAKE_SESSION_TOKEN);
        assertEquals(mockUser, (testObserver.getEvents().get(0)).get(0));
        verifyNoMoreInteractions(mockUserRepository);
        verify(mockSessionRepository).setCurrentUser(mockUser);
        verifyNoMoreInteractions(mockSessionRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}