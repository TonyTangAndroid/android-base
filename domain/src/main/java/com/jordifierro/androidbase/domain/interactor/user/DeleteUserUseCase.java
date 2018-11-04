package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.CompletableUseCase;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteUserUseCase extends CompletableUseCase {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    @Inject
    public DeleteUserUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             UserRepository userRepository, SessionRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected Completable build() {
        return this.userRepository.deleteUser(this.sessionRepository.getCurrentUser())
                .doOnComplete(() -> sessionRepository.invalidateSession());
    }
}
