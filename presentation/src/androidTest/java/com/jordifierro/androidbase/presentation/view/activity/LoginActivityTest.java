package com.jordifierro.androidbase.presentation.view.activity;

import android.content.pm.PackageManager;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.LoginFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(
        LoginActivity.class);
    private LoginFragment loginFragment;

    @Before
    public void setUp() throws Exception {
        this.loginFragment = ((LoginFragment) this.activityTestRule.getActivity()
            .getFragmentManager().findFragmentById(R.id.fragment_container));
    }

    @Test
    public void testViewElements() throws PackageManager.NameNotFoundException {
        onView(withId(R.id.et_email)).check(matches(withHint(R.string.edittext_email)));
        onView(withId(R.id.et_password)).check(matches(withHint(R.string.edittext_password)));
        onView(withId(R.id.btn_login)).check(matches(withText(R.string.button_login)));
        onView(withId(R.id.btn_register)).check(matches(withText(R.string.button_register)));
        onView(withId(R.id.tv_forgot_password))
            .check(matches(withText(R.string.textview_forgot_password)));
    }

    @Test
    public void testViewNotes() {
        Intents.init();

        this.loginFragment.viewNotes();

        Intents.release();
    }

    @Test
    public void testRegisterButton() {

    }

    @Test
    public void testForgotPasswordClick() {

    }

}
