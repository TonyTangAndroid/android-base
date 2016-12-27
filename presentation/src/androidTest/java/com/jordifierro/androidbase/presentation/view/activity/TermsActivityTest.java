package com.jordifierro.androidbase.presentation.view.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.WebViewFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TermsActivityTest {

    @Rule
    public final ActivityTestRule<TermsActivity> activityTestRule =
            new ActivityTestRule<>(TermsActivity.class, false, true);
    private WebViewFragment webViewFragment;

    @Before
    public void setUp() throws Exception {
        this.webViewFragment = ((WebViewFragment) this.activityTestRule.getActivity()
                .getFragmentManager().findFragmentById(R.id.fragment_container));
    }

    @Test
    public void testViewElements() throws InterruptedException {
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(R.string.title_activity_terms)));
        onView(withId(R.id.webview)).check(matches(isDisplayed()));
    }

    @Test
    public void testUrl() {
        assertEquals(RestApi.URL_BASE + "/terms", this.activityTestRule.getActivity().getUrl());
    }

}
