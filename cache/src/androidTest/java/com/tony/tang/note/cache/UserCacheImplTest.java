package com.tony.tang.note.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.tony.tang.note.data.TokenCache;

public class UserCacheImplTest extends InstrumentationTestCase {

    private static final String SHARED_PACKAGE = "SharedPreferencesTest";
    private static final String FAKE_JSON = "test@email.com";

    private TokenCache tokenCache;

    protected void setUp() throws Exception {
        super.setUp();
        SharedPreferences sharedPreferences = getInstrumentation().getTargetContext()
                .getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        this.tokenCache = new TokenCacheImpl(sharedPreferences);
    }

    public void testGetWithoutSetReturnsNull() {
        assertNull(this.tokenCache.get());
    }

    public void testSetAndGetSettedUser() {
        this.tokenCache.save(FAKE_JSON);
        String actual = this.tokenCache.get();

        Truth.assertThat(actual, FAKE_JSON);
    }

    public void testSetInvalidateAndGetNullUser() {
        this.tokenCache.remove();
        assertNull(this.tokenCache.get());
    }
}
