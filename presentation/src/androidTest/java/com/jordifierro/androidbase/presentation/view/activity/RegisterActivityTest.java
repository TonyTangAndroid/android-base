package com.jordifierro.androidbase.presentation.view.activity;

import android.content.pm.PackageManager;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.RegisterFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public final ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>(
        RegisterActivity.class);
    private RegisterFragment registerFragment;

    @Before
    public void setUp() throws Exception {
        this.registerFragment = ((RegisterFragment) this.activityTestRule.getActivity()
            .getFragmentManager().findFragmentById(R.id.fragment_container));
    }

    @Test
    public void testViewElements() throws PackageManager.NameNotFoundException {
        onView(withId(R.id.et_email)).check(matches(withHint(R.string.edittext_email)));
        onView(withId(R.id.et_password)).check(matches(withHint(R.string.edittext_password)));
        onView(withId(R.id.et_password_confirmation))
            .check(matches(withHint(R.string.edittext_password_confirmation)));
        onView(withId(R.id.btn_register)).check(matches(withText(R.string.button_register)));
        onView(withId(R.id.tv_terms)).check(matches(withText(R.string.textview_register_terms)));
    }

    @Test
    public void testLoginButton() {

        onView(withId(R.id.et_email)).perform(typeText("email@test.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("87654321"), closeSoftKeyboard());
        onView(withId(R.id.et_password_confirmation))
            .perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_register)).perform(click());

        verify(this.registerFragment.getRegisterPresenter())
            .registerUser("email@test.com", "87654321", "1234");
    }

    @Test
    public void testNavigateToNotes() {
        Intents.init();

        this.registerFragment.viewNotes();

        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testTermsClicked() {
        Intents.init();

        onView(withId(R.id.tv_terms)).perform(click());

        intended(hasComponent(TermsActivity.class.getName()));
        Intents.release();
    }
}
