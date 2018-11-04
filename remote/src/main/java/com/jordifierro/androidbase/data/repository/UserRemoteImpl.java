package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.repository.UserRemote;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRemoteImpl implements UserRemote {

    private final RestApi restApi;

    @Inject
    public UserRemoteImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<CreatedWrapper> createUser(UserEntity user) {
        return this.restApi.createUser(user).flatMap(Validator::validate);
    }


    @Override
    public Completable deleteUser(final UserEntity user) {
        return this.restApi.deleteUser(user.getObjectId())
                .flatMap(Validator::validate).ignoreElement();
    }

    @Override
    public Completable resetPassword(UserEntity user) {
        Map<String, Object> params = new HashMap<>();
        params.put(RestApi.FIELD_EMAIL, user.getEmail());
        return this.restApi.resetPassword(params).flatMap(Validator::validate).ignoreElement();
    }

    @Override
    public Single<UserEntity> getUserBySessionToken(String token) {
        return this.restApi.getUserBySessionToken(token).flatMap(Validator::validate);
    }

    @Override
    public Single<UserEntity> loginUser(UserEntity user) {
        return this.restApi.doLogin(user.getUsername(), user.getPassword())
                .flatMap(Validator::validate);
    }

    @Override
    public Completable logoutUser(UserEntity user) {
        return this.restApi.doLogout(user.getSessionToken())
                .flatMap(Validator::validate).ignoreElement();
    }
}
