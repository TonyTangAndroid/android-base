package com.tony.tang.note.domain.interactor.user;

import com.tony.tang.note.domain.entity.UserEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.SingleUseCase;
import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.domain.repository.UserRepository;

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
