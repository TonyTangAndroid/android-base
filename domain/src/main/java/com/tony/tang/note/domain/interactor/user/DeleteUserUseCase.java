package com.tony.tang.note.domain.interactor.user;

import com.tony.tang.note.domain.entity.UserEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.domain.repository.UserRepository;

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
