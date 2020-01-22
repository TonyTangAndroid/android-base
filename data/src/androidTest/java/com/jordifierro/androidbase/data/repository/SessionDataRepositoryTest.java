package com.jordifierro.androidbase.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.jordifierro.androidbase.domain.entity.UserEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class SessionDataRepositoryTest {

    private static final String SHARED_PACKAGE = "SharedPreferencesTest";
    private static final String FAKE_EMAIL = "test@email.com";
    private static final String FAKE_TOKEN = "1234TOKEN";

    private SharedPreferences sharedPreferences;
    private SessionDataRepository sessionDataRepository;
    private UserEntity user;

    @Before
    public void setUp() throws Exception {
        this.sharedPreferences = InstrumentationRegistry.getInstrumentation().getTargetContext()
            .getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        this.sessionDataRepository = new SessionDataRepository(this.sharedPreferences);
        this.user = new UserEntity(FAKE_EMAIL);
        this.user.setAuthToken(FAKE_TOKEN);
    }

    @Test
    public void testGetWithoutSetReturnsNull() {
        assertNull(this.sessionDataRepository.getCurrentUser().getEmail());
        assertNull(this.sessionDataRepository.getCurrentUser().getAuthToken());
    }

    @Test
    public void testSetAndGetSettedUser() {
        this.sessionDataRepository.setCurrentUser(this.user);
        UserEntity currentUser = this.sessionDataRepository.getCurrentUser();

        assertEquals(currentUser.getEmail(), FAKE_EMAIL);
        assertEquals(currentUser.getAuthToken(), FAKE_TOKEN);
    }

    @Test
    public void testSetInvalidateAndGetNullUser() {
        this.sessionDataRepository.setCurrentUser(this.user);
        this.sessionDataRepository.invalidateSession();

        assertNull(this.sessionDataRepository.getCurrentUser().getEmail());
        assertNull(this.sessionDataRepository.getCurrentUser().getAuthToken());
    }
}
