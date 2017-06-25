package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.interactor.user.CreateUserUseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.RegisterView;

import javax.inject.Inject;


public class RegisterPresenter extends BasePresenter implements Presenter {

    private RegisterView registerView;
    private CreateUserUseCase createUserUseCase;

    @Inject

    public RegisterPresenter(CreateUserUseCase createUserUseCase) {
        super(createUserUseCase);
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    protected RegisterView getCleanView() {
        return registerView;
    }

    @Override
    public void bindPresenter(CleanView view) {
        this.registerView = (RegisterView) view;
    }

    @Override
    public void destroy() {
        super.destroy();
        this.registerView = null;
    }

    public void registerUser(String email, String password, String passwordConfirmation) {
        UserEntity user = new UserEntity(email);
        user.setPassword(password);
        this.showLoader();
        this.createUserUseCase.setParams(user);
        this.createUserUseCase.execute(new RegisterSubscriber());
    }

    protected class RegisterSubscriber extends BaseSubscriber<UserEntity> {

        @Override
        public void onNext(UserEntity user) {
            RegisterPresenter.this.hideLoader();
            RegisterPresenter.this.registerView.viewNotes();
        }

    }

}
