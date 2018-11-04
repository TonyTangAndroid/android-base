package com.tony.tang.note.domain.interactor.user;

import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.domain.repository.UserRepository;

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
