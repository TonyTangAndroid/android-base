package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DoLogoutUseCase extends UseCase<VoidEntity> {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    @Inject
    public DoLogoutUseCase(ThreadExecutor threadExecutor, UIThread UIThread,
                           UserRepository userRepository, SessionRepository sessionRepository) {
        super(threadExecutor, UIThread);
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected Observable<VoidEntity> build() {
        UserEntity currentUser = this.sessionRepository.getCurrentUser();
        this.sessionRepository.invalidateSession();
        return this.userRepository.logoutUser(currentUser);
    }
}
