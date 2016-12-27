package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Response;

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
                .flatMap(new Function<Response<CreatedWrapper>, ObservableSource<UserEntity>>() {
                    @Override
                    public ObservableSource<UserEntity> apply(Response<CreatedWrapper> createdWrapperResponse) throws Exception {
                        handleResponseError(createdWrapperResponse);
                        final CreatedWrapper body = createdWrapperResponse.body();
                        return getUserBySessionToken(body.getSessionToken());

                    }
                });
    }

    @Override
    public Observable<CreatedWrapper> createUserWithRawResponse(UserEntity user) {
        return this.restApi.createUser(user).map(new Function<Response<CreatedWrapper>, CreatedWrapper>() {
            @Override
            public CreatedWrapper apply(Response<CreatedWrapper> createdWrapperResponse) throws Exception {

                handleResponseError(createdWrapperResponse);
                return createdWrapperResponse.body();

            }
        });

    }

    @Override
    public Observable<VoidEntity> deleteUser(final UserEntity user) {
        return this.restApi.deleteUser(user.getSessionToken(), user.getObjectId())
                .map(new Function<Response<VoidEntity>, VoidEntity>() {
                    @Override
                    public VoidEntity apply(Response<VoidEntity> voidResponse) {
                        handleResponseError(voidResponse);
                        return voidResponse.body();
                    }
                });
    }

    @Override
    public Observable<VoidEntity> resetPassword(UserEntity user) {
        Map<String, Object> params = new HashMap<>();
        params.put(RestApi.FIELD_EMAIL, user.getEmail());
        return this.restApi.resetPassword(params).map(new Function<Response<VoidEntity>, VoidEntity>() {
            @Override
            public VoidEntity apply(Response<VoidEntity> voidResponse) {
                handleResponseError(voidResponse);
                return voidResponse.body();
            }
        });
    }

    @Override
    public Observable<UserEntity> getUserBySessionToken(String token) {
        return this.restApi.getUserBySessionToken(token).map(new Function<Response<UserEntity>, UserEntity>() {
            @Override
            public UserEntity apply(Response<UserEntity> userEntityResponse) {
                handleResponseError(userEntityResponse);
                return userEntityResponse.body();
            }
        });
    }

    @Override
    public Observable<UserEntity> loginUser(UserEntity user) {
        return this.restApi.doLogin(user.getUsername(), user.getPassword())
                .map(new Function<Response<UserEntity>, UserEntity>() {
                    @Override
                    public UserEntity apply(Response<UserEntity> userEntityResponse) {
                        handleResponseError(userEntityResponse);
                        return userEntityResponse.body();
                    }
                });
    }

    @Override
    public Observable<VoidEntity> logoutUser(UserEntity user) {
        return this.restApi.doLogout(user.getSessionToken())
                .map(new Function<Response<VoidEntity>, VoidEntity>() {
                    @Override
                    public VoidEntity apply(Response<VoidEntity> emptyWrapperResponse) {
                        handleResponseError(emptyWrapperResponse);
                        return emptyWrapperResponse.body();
                    }
                });
    }
}
