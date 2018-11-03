package com.jordifierro.androidbase.data.repository;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.utils.TestUtils;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.exception.RestApiErrorException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jordifierro.androidbase.data.net.RestApi.PARSE_SESSION_KEY;
import static junit.framework.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class UserDataRepositoryTest extends BaseDataRepositoryTest {

    private UserDataRepositoryV2 userDataRepository;
    private TestObserver testObserver;
    private MockWebServer mockWebServer;
    private UserEntity fakeUser;
    private Gson gson;

    @Before
    public void setUp() throws IOException {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                .create();

        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        this.userDataRepository = new UserDataRepositoryV2(
                new Retrofit.Builder()
                        .baseUrl(mockWebServer.url(MOCK_SERVER))
                        .addConverterFactory(GsonConverterFactory.create(this.gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(RestApi.class)
        );

        this.testObserver = new TestObserver();

        this.fakeUser = new UserEntity(MOCK_EMAIL);
        this.fakeUser.setObjectId(MOCK_USER_OBJECT_ID);
        this.fakeUser.setPassword(MOCK_PASS);
        this.fakeUser.setSessionToken(MOCK_AUTH_TOKEN);
    }

    @After
    public void tearDown() throws Exception {
        this.mockWebServer.shutdown();
    }

    @Test
    public void testGetUserBySessionTokenRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.getUserBySessionToken(MOCK_AUTH_TOKEN).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();

        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_USERS_ME));
        Truth.assertThat(request.getMethod()).isEqualTo("GET");
        Truth.assertThat(request.getHeader(PARSE_SESSION_KEY)).isEqualTo(MOCK_AUTH_TOKEN);
    }

    @Test
    public void testGetUserBySessionTokenError() throws Exception {
        String json = TestUtils.json("user_me_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                json));

        this.userDataRepository.getUserBySessionToken(MOCK_AUTH_TOKEN).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(209);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid session token");
    }

    @Test
    public void testGetUserBySessionTokenSuccess() throws Exception {

        String json = TestUtils.json("user_me_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));

        this.userDataRepository.getUserBySessionToken(MOCK_AUTH_TOKEN).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        UserEntity responseUser = (UserEntity) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseUser.getSessionToken().length() > 0);
        assertTrue(responseUser.getObjectId().length() > 0);
        assertTrue(responseUser.getUsername().length() > 0);
        assertTrue(responseUser.getEmail().length() > 0);
        assertTrue(responseUser.getCreatedAt().length() > 0);
    }


    @Test
    public void testCreateUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());
        this.userDataRepository.createUser(this.fakeUser).subscribe(this.testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_USERS));
        Truth.assertThat(request.getMethod()).isEqualTo("POST");
        Truth.assertThat(request.getBody().readUtf8()).isEqualTo(this.gson.toJson(this.fakeUser));
    }


    @Test
    public void testCreateUserError() throws Exception {
        String json = TestUtils.json("user_create_error.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(json));

        this.userDataRepository.createUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(203);
        Truth.assertThat(error.getMessage()).isEqualTo("the email address ztangmstr@gmail.com has already been taken");
    }


    @Test
    public void testCreateUserSuccess() throws Exception {

        String json = TestUtils.json("user_create_raw_ok.json", this);
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(json));


        this.userDataRepository.createUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        CreatedWrapper responseUser = (CreatedWrapper) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseUser.getSessionToken().length() > 0);
        assertTrue(responseUser.getObjectId().length() > 0);
        assertTrue(responseUser.getCreatedAt().length() > 0);
    }


    @Test
    public void testDeleteUserSuccess() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

        this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();
        this.testObserver.assertNoErrors();
        this.testObserver.assertComplete();
    }


    @Test
    public void testDeleteUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(getFormattedUrl(fakeUser.getObjectId(), RestApi.URL_PATH_USERS_OBJECT_ID));
        Truth.assertThat(request.getMethod()).isEqualTo("DELETE");
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }


    @Test
    public void testDeleteUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(TestUtils.json("user_delete_error.json", this)));

        this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(209);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid session token");
    }

    @Test
    public void testResetPasswordRequest() throws Exception {
        this.fakeUser.setEmail(MOCK_EMAIL);
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_REQUEST_PASSWORD_RESET));
        Truth.assertThat(request.getMethod()).isEqualTo("POST");

        Map<String, Object> params = new HashMap<>();
        params.put(RestApi.FIELD_EMAIL, this.fakeUser.getEmail());
        Truth.assertThat(request.getBody().readUtf8()).isEqualTo(new Gson().toJson(params));
    }

    @Test
    public void testResetPasswordSuccess() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

        this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertNoErrors();
        this.testObserver.assertComplete();
    }

    @Test
    public void testResetPasswordError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                TestUtils.json("reset_password_error.json", this)));

        this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(205);
        Truth.assertThat(error.getMessage()).isEqualTo("no user found with email ztangmstrx1@gmail.com");
    }

    @Test
    public void testLoginUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());
        this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testObserver);
        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getMethod()).isEqualTo("GET");
        final String path = request.getPath();
        Truth.assertThat(path).endsWith("username=tangzhilu%40mail.com&password=1234");
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                TestUtils.json("session_login_ok.json", this)));


        this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();
        this.testObserver.assertValueCount(1);

        UserEntity responseUser = (UserEntity) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseUser.getUsername().length() > 0);
        assertTrue(responseUser.getSessionToken().length() > 0);
    }

    @Test
    public void testLogoutUserSuccess() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();
        this.testObserver.assertComplete();
    }

    @Test
    public void testLoginUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(
                TestUtils.json("session_login_error.json", this)));


        this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(101);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid login parameters");
    }

    @Test
    public void testLogoutUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo(constructUrl(RestApi.URL_PATH_LOGOUT));
        Truth.assertThat(request.getMethod()).isEqualTo("POST");
        Truth.assertThat(request.getHeader(PARSE_SESSION_KEY)).isEqualTo(MOCK_AUTH_TOKEN);
        Truth.assertThat(request.getBody().readUtf8()).isEmpty();
    }

    @Test
    public void testLogoutUserError() {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("{\n" +
                "  \"code\": 209,\n" +
                "  \"error\": \"invalid session token\"\n" +
                "}"));

        this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        Truth.assertThat(error.getStatusCode()).isEqualTo(209);
        Truth.assertThat(error.getMessage()).isEqualTo("invalid session token");
    }

}
