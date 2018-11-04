package com.jordifierro.androidbase.domain.repository;

import com.jordifierro.androidbase.domain.entity.UserEntity;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface UserRepository {
    Single<String> createUser(UserEntity user);

    Completable deleteUser(UserEntity user);

    Completable resetPassword(UserEntity user);

    Single<UserEntity> loginUser(UserEntity user);

    Completable logoutUser(UserEntity user);

    Single<UserEntity> getUserBySessionToken(String token);

}
