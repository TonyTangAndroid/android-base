package com.jordifierro.androidbase.data.repository;

@SuppressWarnings("unchecked")
public class BaseDataRepositoryTest {


	public static final String FAKE_SERVER = "/";
	static final String USER_OBJECT_ID = "ctn1Zeov9d";
	static final String AUTH_TOKEN = "fake_auth_token";
	static final String NOTE_TITLE = "fake_note_title";
	static final String NOTE_CONTENT = "fake_note_content";
	static final String NOTE_OBJECT_ID = "3WQrZ0dyrt";
	static final String FAKE_EMAIL = "tangzhilu@mail.com";
	static final String FAKE_PASS = "1234";

	String getFormattedUrl(String objectId, String urlPath) {
		return FAKE_SERVER + urlPath.replaceAll("\\{objectId\\}", objectId);
	}

	String constructUrl(String path) {
		return FAKE_SERVER + path;
	}
}
