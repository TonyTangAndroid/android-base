package com.tony.tang.note.domain.repository;

import com.tony.tang.note.domain.entity.UserEntity;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface UserRepository {
    Single<String> createUser(UserEntity user);

    Completable deleteUser(String objectId);

    Completable resetPassword(String email);

    Single<UserEntity> loginUser(String username, String password);

    Completable logoutUser();

    Single<UserEntity> getUserBySessionToken();

}