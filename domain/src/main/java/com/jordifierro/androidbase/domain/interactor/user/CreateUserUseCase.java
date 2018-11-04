package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class CreateUserUseCase extends SingleUseCase<UserEntity> {

    private UserRepository userRepository;
    private TokenRepository sessionRepository;

    private UserEntity user;

    @Inject
    public CreateUserUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             UserRepository userRepository, TokenRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public void setParams(UserEntity user) {
        this.user = user;
    }

    @Override
    protected Single<UserEntity> build() {
        return this.userRepository.createUser(this.user)
                .doOnSuccess(this::updateToken)
                .flatMap(token -> getUserBySessionToken());
    }

    private void updateToken(String token) {
        sessionRepository.update(token);
    }

    private Single<UserEntity> getUserBySessionToken() {
        return userRepository.getUserBySessionToken();
    }
}
