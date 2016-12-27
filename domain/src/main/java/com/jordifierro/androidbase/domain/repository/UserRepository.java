package com.jordifierro.androidbase.domain.repository;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import io.reactivex.Observable;


public interface UserRepository {
    Observable<UserEntity> createUser(UserEntity user);

    Observable<CreatedWrapper> createUserWithRawResponse(UserEntity user);

    Observable<VoidEntity> deleteUser(UserEntity user);

    Observable<VoidEntity> resetPassword(UserEntity user);

    Observable<UserEntity> loginUser(UserEntity user);

    Observable<VoidEntity> logoutUser(UserEntity user);

    Observable<UserEntity> me(String token);

}
