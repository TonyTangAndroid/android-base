package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class UserDataRepository extends RestApiRepository implements UserRepository {

	private final RestApi restApi;

	@Inject
	public UserDataRepository(RestApi restApi) {
		this.restApi = restApi;
	}

	@Override
	public Observable<UserEntity> createUser(UserEntity user) {
		return this.restApi.createUser(user)
				.flatMap(new Func1<Response<CreatedWrapper>, Observable<UserEntity>>() {
					@Override
					public Observable<UserEntity> call(Response<CreatedWrapper> userEntityResponse) {
						handleResponseError(userEntityResponse);
						final CreatedWrapper body = userEntityResponse.body();
						return me(body.getSessionToken());
					}
				});
	}

	@Override
	public Observable<CreatedWrapper> createUserWithRawResponse(UserEntity user) {
		return this.restApi.createUser(user).map(new Func1<Response<CreatedWrapper>, CreatedWrapper>() {
			@Override
			public CreatedWrapper call(Response<CreatedWrapper> createdWrapperResponse) {
				handleResponseError(createdWrapperResponse);
				return createdWrapperResponse.body();
			}
		});

	}

	@Override
	public Observable<EmptyWrapper> deleteUser(final UserEntity user) {
		return this.restApi.deleteUser(user.getSessionToken(), user.getObjectId())
				.map(new Func1<Response<EmptyWrapper>, EmptyWrapper>() {
					@Override
					public EmptyWrapper call(Response<EmptyWrapper> voidResponse) {
						handleResponseError(voidResponse);
						return voidResponse.body();
					}
				});
	}

	@Override
	public Observable<EmptyWrapper> resetPassword(UserEntity user) {
		Map<String, Object> params = new HashMap<>();
		params.put(RestApi.FIELD_EMAIL, user.getEmail());
		return this.restApi.resetPassword(params).map(new Func1<Response<EmptyWrapper>, EmptyWrapper>() {
			@Override
			public EmptyWrapper call(Response<EmptyWrapper> voidResponse) {
				handleResponseError(voidResponse);
				return voidResponse.body();
			}
		});
	}

	@Override
	public Observable<UserEntity> me(String token) {
		return this.restApi.me(token).map(new Func1<Response<UserEntity>, UserEntity>() {
			@Override
			public UserEntity call(Response<UserEntity> userEntityResponse) {
				return userEntityResponse.body();
			}
		});
	}

	@Override
	public Observable<UserEntity> loginUser(UserEntity user) {
		return this.restApi.doLogin(user.getUsername(), user.getPassword())
				.map(new Func1<Response<UserEntity>, UserEntity>() {
					@Override
					public UserEntity call(Response<UserEntity> userEntityResponse) {
						handleResponseError(userEntityResponse);
						return userEntityResponse.body();
					}
				});
	}

	@Override
	public Observable<EmptyWrapper> logoutUser(UserEntity user) {
		return this.restApi.doLogout(user.getSessionToken())
				.map(new Func1<Response<EmptyWrapper>, EmptyWrapper>() {
					@Override
					public EmptyWrapper call(Response<EmptyWrapper> emptyWrapperResponse) {
						handleResponseError(emptyWrapperResponse);
						return emptyWrapperResponse.body();
					}
				});
	}
}
