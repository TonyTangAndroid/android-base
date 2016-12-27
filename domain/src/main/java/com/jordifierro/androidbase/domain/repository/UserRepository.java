package com.jordifierro.androidbase.domain.repository;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import rx.Observable;

public interface UserRepository {
	Observable<UserEntity> createUser(UserEntity user);

	Observable<CreatedWrapper> createUserWithRawResponse(UserEntity user);

	Observable<EmptyWrapper> deleteUser(UserEntity user);

	Observable<EmptyWrapper> resetPassword(UserEntity user);

	Observable<UserEntity> loginUser(UserEntity user);

	Observable<EmptyWrapper> logoutUser(UserEntity user);

	Observable<UserEntity> me(String token);

}
