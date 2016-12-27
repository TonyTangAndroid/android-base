package com.jordifierro.androidbase.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.InstrumentationTestCase;

import com.jordifierro.androidbase.domain.entity.UserEntity;

public class SessionDataRepositoryTest extends InstrumentationTestCase {

    private static final String SHARED_PACKAGE = "SharedPreferencesTest";
    private static final String FAKE_EMAIL = "test@email.com";
    private static final String FAKE_TOKEN = "1234TOKEN";
    private static final String FAKE_OBJECT_ID = "3WQrZ0dyrt";

    private SessionDataRepository sessionDataRepository;
    private UserEntity user;

    protected void setUp() throws Exception {
        SharedPreferences sharedPreferences = getInstrumentation().getTargetContext()
                .getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        this.sessionDataRepository = new SessionDataRepository(sharedPreferences);
        this.user = new UserEntity(FAKE_EMAIL);
        this.user.setSessionToken(FAKE_TOKEN);
        this.user.setObjectId(FAKE_OBJECT_ID);
    }

    public void testGetWithoutSetReturnsNull(){
        assertNull(this.sessionDataRepository.getCurrentUser().getUsername());
        assertNull(this.sessionDataRepository.getCurrentUser().getSessionToken());
        assertNull(this.sessionDataRepository.getCurrentUser().getObjectId());
    }

    public void testSetAndGetSettedUser() {
        this.sessionDataRepository.setCurrentUser(this.user);
        UserEntity currentUser = this.sessionDataRepository.getCurrentUser();

        assertEquals(currentUser.getUsername(), FAKE_EMAIL);
        assertEquals(currentUser.getSessionToken(), FAKE_TOKEN);
        assertEquals(currentUser.getObjectId(), FAKE_OBJECT_ID);
    }

    public void testSetInvalidateAndGetNullUser() {
        this.sessionDataRepository.setCurrentUser(this.user);
        this.sessionDataRepository.invalidateSession();

        assertNull(this.sessionDataRepository.getCurrentUser().getObjectId());
        assertNull(this.sessionDataRepository.getCurrentUser().getUsername());
        assertNull(this.sessionDataRepository.getCurrentUser().getSessionToken());
    }
}
