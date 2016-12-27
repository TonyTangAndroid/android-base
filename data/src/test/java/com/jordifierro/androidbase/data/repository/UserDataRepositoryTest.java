package com.jordifierro.androidbase.data.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.net.error.RestApiErrorException;
import com.jordifierro.androidbase.data.utils.TestUtils;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jordifierro.androidbase.data.net.RestApi.PARSE_SESSION_KEY;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class UserDataRepositoryTest extends BaseDataRepositoryTest {

    private UserDataRepository userDataRepository;
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

        this.userDataRepository = new UserDataRepository(
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

        assertEquals(constructUrl(RestApi.URL_PATH_USERS_ME), request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(request.getHeader(PARSE_SESSION_KEY), MOCK_AUTH_TOKEN);
    }


    @Test
    public void testGetUserBySessionTokenError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/user_me_error.json"))));

        this.userDataRepository.getUserBySessionToken(MOCK_AUTH_TOKEN).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(209, error.getStatusCode());
        assertEquals("invalid session token", error.getMessage());
    }


    @Test
    public void testGetUserBySessionTokenSuccess() throws Exception {

        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/user_me_ok.json"))));


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
        assertEquals(constructUrl(RestApi.URL_PATH_USERS), request.getPath());
        assertEquals("POST", request.getMethod());
        assertEquals(this.gson.toJson(this.fakeUser),
                request.getBody().readUtf8());
    }


    @Test
    public void testCreateUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/user_create_error.json"))));

        this.userDataRepository.createUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(203, error.getStatusCode());
        assertEquals("the email address ztangmstr@gmail.com has already been taken", error.getMessage());
    }


    @Test
    public void testCreateUserSuccess() throws Exception {

        //in createUser, we have two request sent to server.
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/user_create_raw_ok.json"))));

        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/user_me_ok.json"))));


        this.userDataRepository.createUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        UserEntity responseUser = (UserEntity) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseUser.getSessionToken().length() > 0);
        assertTrue(responseUser.getObjectId().length() > 0);
        assertTrue(responseUser.getUsername().length() > 0);
        assertTrue(responseUser.getEmail().length() > 0);
        assertTrue(responseUser.getCreatedAt().length() > 0);
    }


    @Test
    public void testCreateUserRawSuccess() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(201).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/user_create_raw_ok.json"))));

        this.userDataRepository.createUserWithRawResponse(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        CreatedWrapper responseUser = (CreatedWrapper) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseUser.getSessionToken().length() > 0);
        assertTrue(responseUser.getObjectId().length() > 0);
        assertTrue(responseUser.getCreatedAt().length() > 0);
    }


    @Test
    public void testDeleteUserSuccess() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

        this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        VoidEntity responseNote = (VoidEntity) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertNotNull(responseNote);
        this.testObserver.assertNoErrors();
        this.testObserver.assertComplete();
    }


    @Test
    public void testDeleteUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();


        assertEquals(getFormattedUrl(fakeUser.getObjectId(), RestApi.URL_PATH_USERS_OBJECT_ID), request.getPath());
        assertEquals("DELETE", request.getMethod());
        assertEquals(MOCK_AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
        assertEquals("", request.getBody().readUtf8());
    }


    @Test
    public void testDeleteUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(FileUtils.readFileToString(
                TestUtils.getFileFromPath(this, "res/user_delete_error.json"))));

        this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(209, error.getStatusCode());
        assertTrue(error.getMessage().equals("invalid session token"));
    }

    @Test
    public void testResetPasswordRequest() throws Exception {
        this.fakeUser.setEmail(MOCK_EMAIL);
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals(constructUrl(RestApi.URL_PATH_REQUEST_PASSWORD_RESET), request.getPath());
        assertEquals("POST", request.getMethod());

        Map<String, Object> params = new HashMap<>();
        params.put(RestApi.FIELD_EMAIL, this.fakeUser.getEmail());
        String query = RestApi.FIELD_EMAIL + "=" + URLEncoder.encode(this.fakeUser.getEmail(), "UTF-8");
        assertEquals(new Gson().toJson(params), request.getBody().readUtf8());
    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

        this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(1);
        this.testObserver.assertNoErrors();
        this.testObserver.assertComplete();
    }

    @Test
    public void testResetPasswordError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/reset_password_error.json"))));

        this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(205, error.getStatusCode());
        assertEquals("no user found with email ztangmstrx1@gmail.com", error.getMessage());
    }

    @Test
    public void testLoginUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());

        String userQuery = RestApi.FIELD_USERNAME + "=" + this.fakeUser.getEmail();
        String passwordQuery = RestApi.FIELD_PASSWORD + "=" + this.fakeUser.getPassword();

        final String path = request.getPath();
        assertTrue(path.contains(userQuery));
        assertTrue(path.contains(passwordQuery));
        assertEquals(request.getBody().readUtf8(), "");
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/session_login_ok.json"))));


        this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();
        this.testObserver.assertValueCount(1);

        UserEntity responseUser = (UserEntity) ((List<Object>) this.testObserver.getEvents().get(0)).get(0);
        assertTrue(responseUser.getUsername().length() > 0);
        assertTrue(responseUser.getSessionToken().length() > 0);
    }

    @Test
    public void testLogoutUserSuccess() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();
        this.testObserver.assertValueCount(1);
    }

    @Test
    public void testLoginUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/session_login_error.json"))));


        this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(101, error.getStatusCode());
        assertEquals("invalid login parameters", error.getMessage());
    }

    @Test
    public void testLogoutUserRequest() throws Exception {
        this.mockWebServer.enqueue(new MockResponse());

        this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testObserver);

        RecordedRequest request = this.mockWebServer.takeRequest();
        assertEquals(constructUrl(RestApi.URL_PATH_LOGOUT), request.getPath());
        assertEquals("POST", request.getMethod());
        assertEquals(MOCK_AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
        assertEquals("", request.getBody().readUtf8());
    }

    @Test
    public void testLogoutUserError() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("{\n" +
                "  \"code\": 209,\n" +
                "  \"error\": \"invalid session token\"\n" +
                "}"));

        this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testObserver);
        this.testObserver.awaitTerminalEvent();

        this.testObserver.assertValueCount(0);
        RestApiErrorException error = (RestApiErrorException)
                this.testObserver.errors().get(0);
        assertEquals(209, error.getStatusCode());
        assertTrue(error.getMessage().equals("invalid session token"));
    }

}
