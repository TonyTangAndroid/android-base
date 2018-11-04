package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.CompletableUseCase;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;


public class ResetPasswordUseCase extends CompletableUseCase {

    private UserRepository userRepository;

    private UserEntity user;

    @Inject
    public ResetPasswordUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                                UserRepository userRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
    }

    public void setParams(UserEntity user) {
        this.user = user;
    }

    @Override
    protected Completable build() {
        return this.userRepository.resetPassword(this.user.email());
    }
}
