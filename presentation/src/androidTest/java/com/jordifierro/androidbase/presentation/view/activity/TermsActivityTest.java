package com.jordifierro.androidbase.presentation.view.activity;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.WebViewFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
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
        onView(allOf(isAssignableFrom(TextView.class),withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(R.string.title_activity_terms)));
        onView(withId(R.id.webview)).check(matches(isDisplayed()));
    }

    @Test
    public void testUrl() {
        assertEquals(RestApi.URL_BASE + "/terms", this.activityTestRule.getActivity().getUrl());
    }

}
