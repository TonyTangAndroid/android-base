package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRemote;

import javax.inject.Inject;

import io.reactivex.Single;

public class DoLoginUseCase extends SingleUseCase<UserEntity> {

    private UserRemote userRepository;
    private SessionRepository sessionRepository;

    private UserEntity user;

    @Inject
    public DoLoginUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                          UserRemote userRepository, SessionRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public void setParams(UserEntity user) {
        this.user = user;
    }

    @Override
    protected Single<UserEntity> build() {
        return this.userRepository.loginUser(this.user)
                .doOnSuccess(this::updateSession);
    }

    private void updateSession(UserEntity userEntity) {
        sessionRepository.setCurrentUser(userEntity);
    }
}
