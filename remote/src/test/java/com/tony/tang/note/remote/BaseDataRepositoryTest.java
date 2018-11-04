package com.tony.tang.note.remote;

public class BaseDataRepositoryTest {


    public static final String MOCK_SERVER = "/";
    static final String MOCK_USER_OBJECT_ID = "ctn1Zeov9d";
    static final String MOCK_AUTH_TOKEN = "fake_auth_token";
    static final String MOCK_NOTE_TITLE = "fake_note_title";
    static final String MOCK_NOTE_CONTENT = "fake_note_content";
    static final String OBJECT_ID = "3WQrZ0dyrt";
    static final String MOCK_EMAIL = "tangzhilu@mail.com";
    static final String MOCK_PASS = "1234";

    String getFormattedUrl(String objectId, String urlPath) {
        return MOCK_SERVER + urlPath.replaceAll("\\{objectId}", objectId);
    }

    String constructUrl(String path) {
        return MOCK_SERVER + path;
    }
}
