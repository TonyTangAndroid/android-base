package com.jordifierro.androidbase.domain.repository;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import rx.Observable;

public interface UserRepository {
	Observable<UserEntity> createUser(UserEntity user);

	Observable<CreatedWrapper> createUserWithRawResponse(UserEntity user);

	Observable<EmptyWrapper> deleteUser(UserEntity user);

	Observable<Void> resetPassword(UserEntity user);

	Observable<UserEntity> loginUser(UserEntity user);

	Observable<Void> logoutUser(UserEntity user);

	Observable<UserEntity> me(String token);

}
