package com.jordifierro.androidbase.domain.repository;

import com.jordifierro.androidbase.domain.entity.UserEntity;

import rx.Observable;

public interface UserRepository {
	Observable<UserEntity> createUser(UserEntity user);

	Observable deleteUser(UserEntity user);

	Observable<Void> resetPassword(UserEntity user);

	Observable<UserEntity> loginUser(UserEntity user);

	Observable<Void> logoutUser(UserEntity user);

	Observable<UserEntity> me(String token);

}
