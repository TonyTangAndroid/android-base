package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRemoteRepository implements UserRepository {

    private final RestApi restApi;

    @Inject
    public UserRemoteRepository(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<String> createUser(UserEntity user) {
        return this.restApi.createUser(user).flatMap(Validator::validate).map(CreatedWrapper::getSessionToken);
    }


    @Override
    public Completable deleteUser(String objectId) {
        return this.restApi.deleteUser(objectId)
                .flatMap(Validator::validate).ignoreElement();
    }

    @Override
    public Completable resetPassword(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put(RestApi.FIELD_EMAIL, email);
        return this.restApi.resetPassword(params).flatMap(Validator::validate).ignoreElement();
    }

    @Override
    public Single<UserEntity> getUserBySessionToken() {
        return this.restApi.getUserBySessionToken().flatMap(Validator::validate);
    }

    @Override
    public Single<UserEntity> loginUser(String username, String password) {
        return this.restApi.doLogin(username, password).flatMap(Validator::validate);
    }

    @Override
    public Completable logoutUser() {
        return this.restApi.doLogout()
                .flatMap(Validator::validate).ignoreElement();
    }
}
