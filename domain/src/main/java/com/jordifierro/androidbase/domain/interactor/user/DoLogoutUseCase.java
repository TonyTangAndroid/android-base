package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.CompletableUseCase;
import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DoLogoutUseCase extends CompletableUseCase {

    private UserRepository userRepository;
    private TokenRepository sessionRepository;

    @Inject
    public DoLogoutUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                           UserRepository userRepository, TokenRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected Completable build() {
        return this.userRepository.logoutUser().doOnComplete(this::invalidate);
    }

    private void invalidate() {
        this.sessionRepository.invalidateSession();
    }
}
