package com.jordifierro.androidbase.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.InstrumentationTestCase;

import com.jordifierro.androidbase.domain.cache.UserCache;

public class UserCacheImplTest extends InstrumentationTestCase {

    private static final String SHARED_PACKAGE = "SharedPreferencesTest";
    private static final String FAKE_JSON = "test@email.com";

    private UserCache sessionDataRepository;

    protected void setUp() throws Exception {
        super.setUp();
        SharedPreferences sharedPreferences = getInstrumentation().getTargetContext()
                .getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        this.sessionDataRepository = new UserCacheImpl(sharedPreferences);
    }

    public void testGetWithoutSetReturnsNull() {
        assertNull(this.sessionDataRepository.get());
    }

    public void testSetAndGetSettedUser() {
        this.sessionDataRepository.save(FAKE_JSON);
        String actual = this.sessionDataRepository.get();

        assertEquals(actual, FAKE_JSON);
    }

    public void testSetInvalidateAndGetNullUser() {
        this.sessionDataRepository.remove();
        assertNull(this.sessionDataRepository.get());
    }
}
