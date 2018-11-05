package com.tony.tang.note.domain.interactor.user;

import com.tony.tang.note.domain.entity.UserEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.repository.UserRepository;

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