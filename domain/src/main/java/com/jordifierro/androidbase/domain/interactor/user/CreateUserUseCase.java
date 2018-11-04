package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class CreateUserUseCase extends SingleUseCase<UserEntity> {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    private UserEntity user;

    @Inject
    public CreateUserUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             UserRepository userRepository, SessionRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public void setParams(UserEntity user) {
        this.user = user;
    }

    @Override
    protected Single<UserEntity> build() {
        return this.userRepository.createUser(this.user).flatMap(this::getUserBySessionToken)
                .doOnSuccess(userEntity -> sessionRepository.setCurrentUser(userEntity));
    }

    private Single<UserEntity> getUserBySessionToken(String sessionToken) {
        return userRepository.getUserBySessionToken(sessionToken);
    }
}
