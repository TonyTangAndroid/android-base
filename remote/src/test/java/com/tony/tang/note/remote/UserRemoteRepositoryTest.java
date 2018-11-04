package com.tony.tang.note.remote;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.tony.tang.note.domain.entity.UserEntity;
import com.tony.tang.note.domain.exception.RestApiErrorException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRemoteRepositoryTest extends BaseDataRepositoryTest {

    private UserRemoteRepository userRemote;
    private MockWebServer mockWebServer;
    private UserEntity userEntity;
    private Gson gson;
    private TestObserver<UserEntity> testObserver;

    @Before
    public void setUp() throws IOException {
        this.testObserver = new TestObserver<>();

        this.gson = WrapperGsonHelper.build();

        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.userRemote = new UserRemoteRepository(
                new Retrofit.Builder()
                        .baseUrl(mockWebServer.url(MOCK_SERVER))
                        .addConverterFactory(GsonConverterFactory.create(this.gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(RestApi.class)
        );

        this.userEntity = UserEntity.typeAdapter(new Gson()).fromJson(TestUtils.json("user_me_ok.json", this));
    }

    @After
    public void tearDown() throws Exception {
        this.mockWebServer.shutdown();
    }

    @Test
    public void testGetUserBySessionTokenRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());


        this.userRemote.getUserBySessionToken().subscribe(testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_USERS_ME));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void testGetUserBySessionTokenError() throws Exception {
        String json = TestUtils.json("user_me_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                json));


        this.userRemote.getUserBySessionToken().subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(209);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid session token");
    }

    @Test
    public void testGetUserBySessionTokenSuccess() throws Exception {


        String json = TestUtils.json("user_me_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));

        this.userRemote.getUserBySessionToken().subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        UserEntity responseUser = (UserEntity) testObserver.getEvents().get(0).get(0);
        Truth.assertThat(responseUser.sessionToken().length() > 0);
        Truth.assertThat(responseUser.objectId().length() > 0);
        Truth.assertThat(responseUser.username().length() > 0);
        Truth.assertThat(responseUser.email().length() > 0);
        Truth.assertThat(responseUser.createdAt().length() > 0);
    }


    @Test
    public void testCreateUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        TestObserver<String> testObserver = new TestObserver<>();
        this.userRemote.createUser(this.userEntity).subscribe(testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_USERS));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        Truth.assertThat(request.getBody().readUtf8()).isEqualTo(this.gson.toJson(this.userEntity));
    }


    @Test
    public void testCreateUserError() throws Exception {
        String json = TestUtils.json("user_create_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(json));
        TestObserver<String> testObserver = new TestObserver<>();


        this.userRemote.createUser(this.userEntity).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException) testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(203);
        Truth.assertThat(error.getMessage()).isEqualTo("the email address ztangmstr@gmail.com has already been taken");
    }


    @Test
    public void testCreateUserSuccess() throws Exception {

        String json = TestUtils.json("user_create_raw_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(json));
        TestObserver<String> testObserver = new TestObserver<>();
        this.userRemote.createUser(this.userEntity).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        Truth.assertThat(testObserver.values()).isEqualTo(Collections.singletonList("r:LhHHilHJ4tgD7762f5SG19mmd"));
    }


    @Test
    public void testDeleteUserSuccess() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        this.userRemote.deleteUser(MOCK_USER_OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }


    @Test
    public void testDeleteUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());


        this.userRemote.deleteUser(MOCK_USER_OBJECT_ID).subscribe(testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(MOCK_USER_OBJECT_ID, RestApi.URL_PATH_USERS_OBJECT_ID));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.DELETE);
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }


    @Test
    public void testDeleteUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(TestUtils.json("user_delete_error.json", this)));


        this.userRemote.deleteUser(MOCK_USER_OBJECT_ID).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(209);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid session token");
    }

    @Test
    public void testResetPasswordRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());


        this.userRemote.resetPassword(MOCK_EMAIL).subscribe(testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_REQUEST_PASSWORD_RESET));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        Truth.assertThat(request.getBody().readUtf8()).isEqualTo(expected());
    }

    private String expected() {
        Map<String, Object> params = new HashMap<>();
        params.put(RestApi.FIELD_EMAIL, MOCK_EMAIL);
        return new Gson().toJson(params);
    }

    @Test
    public void testResetPasswordSuccess() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));


        this.userRemote.resetPassword(this.userEntity.email()).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void testResetPasswordError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                TestUtils.json("reset_password_error.json", this)));


        this.userRemote.resetPassword(this.userEntity.email()).subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(205);
        Truth.assertThat(error.getMessage()).isEqualTo("no user found with email ztangmstrx1@gmail.com");
    }

    @Test
    public void testLoginUserRequest() throws Exception {


        this.mockWebServer.enqueue(new MockResponse());
        this.userRemote.loginUser(this.userEntity.username(), "1234").subscribe(testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        final String path = request.getPath();
        Truth.assertThat(path).endsWith("username=tangzhilu%40gmail.com&password=1234");
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        String json = TestUtils.json("session_login_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                json));


        this.userRemote.loginUser(this.userEntity.username(), "").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertValueCount(1);
        UserEntity expected = UserEntity.typeAdapter(new Gson()).fromJson(json);
        UserEntity actual = testObserver.values().get(0);
        Truth.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testLogoutUserSuccess() {


        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        this.userRemote.logoutUser().subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
    }

    @Test
    public void testLoginUserError() throws Exception {


        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(
                TestUtils.json("session_login_error.json", this)));


        this.userRemote.loginUser(this.userEntity.username(), "").subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(101);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid login parameters");
    }

    @Test
    public void testLogoutUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());


        this.userRemote.logoutUser().subscribe(testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_LOGOUT));
        Truth.assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }

    @Test
    public void testLogoutUserError() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("{\n" +
                "  \"code\": 209,\n" +
                "  \"error\": \"invalid session token\"\n" +
                "}"));

        this.userRemote.logoutUser().subscribe(testObserver);
        testObserver.awaitTerminalEvent();

        testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(209);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid session token");
    }

}
