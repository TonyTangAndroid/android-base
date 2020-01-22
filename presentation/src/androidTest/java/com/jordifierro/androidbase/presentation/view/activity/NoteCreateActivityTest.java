package com.jordifierro.androidbase.presentation.view.activity;

import android.content.pm.PackageManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.NoteCreateFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NoteCreateActivityTest {

    @Rule
    public final ActivityTestRule<NoteCreateActivity> activityTestRule = new ActivityTestRule<>(
        NoteCreateActivity.class);
    private NoteCreateFragment noteCreateFragment;

    @Before
    public void setUp() throws Exception {
        this.noteCreateFragment = ((NoteCreateFragment) this.activityTestRule.getActivity()
            .getFragmentManager().findFragmentById(R.id.fragment_container));
    }

    @Test
    public void testViewElements() throws PackageManager.NameNotFoundException {
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
            .check(matches(withText(R.string.title_activity_note_create)));
        onView(withId(R.id.btn_submit)).check(matches(withText(R.string.button_create)));
        onView(withId(R.id.et_title)).check(matches(withHint(R.string.edittext_title)));
        onView(withId(R.id.et_content)).check(matches(withHint(R.string.edittext_content)));
    }

    @Test
    public void testCreateNote() {

        onView(withId(R.id.et_title)).perform(typeText("Title"), closeSoftKeyboard());
        onView(withId(R.id.et_content)).perform(typeText("Content"));
        onView(withId(R.id.btn_submit)).perform(click());

        verify(this.noteCreateFragment.getNoteCreatePresenter()).createButtonPressed("Title",
            "Content");
    }

}
