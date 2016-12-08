package com.jordifierro.androidbase.data.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.net.error.RestApiErrorException;
import com.jordifierro.androidbase.data.utils.TestUtils;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.EmptyWrapper;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static com.jordifierro.androidbase.data.net.RestApi.PARSE_SESSION_KEY;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class UserDataRepositoryTest extends BaseDataRepositoryTest {

	private UserDataRepository userDataRepository;
	private TestSubscriber testSubscriber;
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
						.baseUrl(mockWebServer.url(FAKE_SERVER))
						.addConverterFactory(GsonConverterFactory.create(this.gson))
						.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
						.build()
						.create(RestApi.class)
		);

		this.testSubscriber = new TestSubscriber();

		this.fakeUser = new UserEntity(FAKE_EMAIL);
		this.fakeUser.setObjectId(USER_OBJECT_ID);
		this.fakeUser.setPassword(FAKE_PASS);
		this.fakeUser.setSessionToken(AUTH_TOKEN);
	}

	@After
	public void tearDown() throws Exception {
		this.mockWebServer.shutdown();
	}


	@Test
	public void testCreateUserError() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
				FileUtils.readFileToString(
						TestUtils.getFileFromPath(this, "res/user_create_error.json"))));

		this.userDataRepository.createUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		this.testSubscriber.assertValueCount(0);
		RestApiErrorException error = (RestApiErrorException)
				this.testSubscriber.getOnErrorEvents().get(0);
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


		this.userDataRepository.createUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		UserEntity responseUser = (UserEntity) this.testSubscriber.getOnNextEvents().get(0);
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

		this.userDataRepository.createUserWithRawResponse(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		CreatedWrapper responseUser = (CreatedWrapper) this.testSubscriber.getOnNextEvents().get(0);
		assertTrue(responseUser.getSessionToken().length() > 0);
		assertTrue(responseUser.getObjectId().length() > 0);
		assertTrue(responseUser.getCreatedAt().length() > 0);
	}


	@Test
	public void testDeleteUserSuccess() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

		this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		EmptyWrapper responseNote = (EmptyWrapper) this.testSubscriber.getOnNextEvents().get(0);
		assertNotNull(responseNote);
		this.testSubscriber.assertNoErrors();
		this.testSubscriber.assertCompleted();
	}


	@Test
	public void testDeleteUserRequest() throws Exception {
		this.mockWebServer.enqueue(new MockResponse());

		this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testSubscriber);

		RecordedRequest request = this.mockWebServer.takeRequest();


		assertEquals(getFormattedUrl(fakeUser.getObjectId(), RestApi.URL_PATH_USERS_OBJECT_ID), request.getPath());
		assertEquals("DELETE", request.getMethod());
		assertEquals(AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
		assertEquals("", request.getBody().readUtf8());
	}

	@Test
	public void testCreateUserRequest() throws Exception {
		this.mockWebServer.enqueue(new MockResponse());

		this.userDataRepository.createUser(this.fakeUser).subscribe(this.testSubscriber);

		RecordedRequest request = this.mockWebServer.takeRequest();
		assertEquals(constructUrl(RestApi.URL_PATH_USERS), request.getPath());
		assertEquals("POST", request.getMethod());
		assertEquals(this.gson.toJson(this.fakeUser),
				request.getBody().readUtf8());
	}


	@Test
	public void testDeleteUserError() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(FileUtils.readFileToString(
				TestUtils.getFileFromPath(this, "res/user_delete_error.json"))));

		this.userDataRepository.deleteUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		this.testSubscriber.assertValueCount(0);
		RestApiErrorException error = (RestApiErrorException)
				this.testSubscriber.getOnErrorEvents().get(0);
		assertEquals(209, error.getStatusCode());
		assertTrue(error.getMessage().equals("invalid session token"));
	}

	@Test
	public void testResetPasswordRequest() throws Exception {
		this.fakeUser.setEmail(FAKE_EMAIL);
		this.mockWebServer.enqueue(new MockResponse());

		this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testSubscriber);

		RecordedRequest request = this.mockWebServer.takeRequest();
		assertEquals(constructUrl(RestApi.URL_PATH_REQUEST_PASSWORD_RESET), request.getPath());
		assertEquals("POST", request.getMethod());

		Map<String , Object> params =new HashMap<>();
		params.put(RestApi.FIELD_EMAIL, this.fakeUser.getEmail());
		String query = RestApi.FIELD_EMAIL + "=" + URLEncoder.encode(this.fakeUser.getEmail(), "UTF-8");
		assertEquals(new Gson().toJson(params), request.getBody().readUtf8());
	}

	@Test
	public void testResetPasswordSuccess() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

		this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		this.testSubscriber.assertValueCount(1);
		this.testSubscriber.assertNoErrors();
		this.testSubscriber.assertCompleted();
	}

	@Test
	public void testResetPasswordError() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody(
				FileUtils.readFileToString(
						TestUtils.getFileFromPath(this, "res/reset_password_error.json"))));

		this.userDataRepository.resetPassword(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		this.testSubscriber.assertValueCount(0);
		RestApiErrorException error = (RestApiErrorException)
				this.testSubscriber.getOnErrorEvents().get(0);
		assertEquals(205, error.getStatusCode());
		assertEquals("no user found with email ztangmstrx1@gmail.com", error.getMessage());
	}

	@Test
	public void testLoginUserRequest() throws Exception {
		this.mockWebServer.enqueue(new MockResponse());

		this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testSubscriber);

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


		this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();
		this.testSubscriber.assertValueCount(1);

		UserEntity responseUser = (UserEntity) this.testSubscriber.getOnNextEvents().get(0);
		assertTrue(responseUser.getUsername().length() > 0);
		assertTrue(responseUser.getSessionToken().length() > 0);
	}

	@Test
	public void testLogoutUserSuccess() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
		this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();
		this.testSubscriber.assertValueCount(1);
	}

	@Test
	public void testLoginUserError() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(422).setBody(
				FileUtils.readFileToString(
						TestUtils.getFileFromPath(this, "res/session_login_error.json"))));


		this.userDataRepository.loginUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		this.testSubscriber.assertValueCount(0);
		RestApiErrorException error = (RestApiErrorException)
				this.testSubscriber.getOnErrorEvents().get(0);
		assertEquals(101, error.getStatusCode());
		assertEquals("invalid login parameters", error.getMessage());
	}

	@Test
	public void testLogoutUserRequest() throws Exception {
		this.mockWebServer.enqueue(new MockResponse());

		this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testSubscriber);

		RecordedRequest request = this.mockWebServer.takeRequest();
		assertEquals(constructUrl(RestApi.URL_PATH_LOGOUT), request.getPath());
		assertEquals("POST", request.getMethod());
		assertEquals(AUTH_TOKEN, request.getHeader(PARSE_SESSION_KEY));
		assertEquals("", request.getBody().readUtf8());
	}

	@Test
	public void testLogoutUserError() throws Exception {
		this.mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("{\n" +
				"  \"code\": 209,\n" +
				"  \"error\": \"invalid session token\"\n" +
				"}"));

		this.userDataRepository.logoutUser(this.fakeUser).subscribe(this.testSubscriber);
		this.testSubscriber.awaitTerminalEvent();

		this.testSubscriber.assertValueCount(0);
		RestApiErrorException error = (RestApiErrorException)
				this.testSubscriber.getOnErrorEvents().get(0);
		assertEquals(209, error.getStatusCode());
		assertTrue(error.getMessage().equals("invalid session token"));
	}

}
