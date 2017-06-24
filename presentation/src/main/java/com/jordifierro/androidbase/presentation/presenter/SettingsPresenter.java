package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.interactor.user.DeleteUserUseCase;
import com.jordifierro.androidbase.domain.interactor.user.DoLogoutUseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.SettingsView;

import javax.inject.Inject;


public class SettingsPresenter extends BasePresenter implements Presenter {

    private SettingsView settingsView;
    private DoLogoutUseCase doLogoutUseCase;
    private DeleteUserUseCase deleteUserUseCase;

    @Inject

    public SettingsPresenter(DoLogoutUseCase doLogoutUseCase, DeleteUserUseCase deleteUserUseCase) {
        super(doLogoutUseCase, deleteUserUseCase);
        this.doLogoutUseCase = doLogoutUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @Override
    protected SettingsView getCleanView() {
        return settingsView;
    }

    @Override
    public void bindPresenter(CleanView view) {
        this.settingsView = (SettingsView) view;
    }

    @Override
    public void destroy() {
        super.destroy();
        this.settingsView = null;
    }

    public void logoutUserButtonPressed() {
        this.doLogoutUseCase.execute(new SettingsSubscriber());
        this.settingsView.closeAndDisplayLogin();
    }

    public void deleteAccountButtonPressed() {
        this.deleteUserUseCase.execute(new SettingsSubscriber());
        this.settingsView.closeAndDisplayLogin();
    }


    protected class SettingsSubscriber extends BaseSubscriber<VoidEntity> {


    }

}
