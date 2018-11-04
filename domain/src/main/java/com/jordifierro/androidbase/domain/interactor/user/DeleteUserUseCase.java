package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.interactor.CompletableUseCase;
import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;

public class DeleteUserUseCase extends CompletableUseCase {

    private UserRepository userRepository;
    private TokenRepository sessionRepository;

    @Inject
    public DeleteUserUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                             UserRepository userRepository, TokenRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected Completable build() {
        return userRepository.getUserBySessionToken().map(UserEntity::objectId).flatMapCompletable(this::delete);
    }

    private CompletableSource delete(String objectId) {
        return userRepository.deleteUser(objectId)
                .doOnComplete(() -> sessionRepository.invalidateSession());
    }


}
