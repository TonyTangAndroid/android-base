package com.jordifierro.androidbase.domain.interactor.user;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class ResetPasswordUseCase extends UseCase {

    private UserRepository userRepository;

    private UserEntity user;

    @Inject
    public ResetPasswordUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
								UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setParams(UserEntity user) {
        this.user = user;
    }

    @Override
    public void execute(Subscriber useCaseSubscriber) {
        super.execute(useCaseSubscriber);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.resetPassword(this.user);
    }
}
