package com.jordifierro.androidbase.presentation.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.NoteEditFragment;

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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NoteEditActivityTest {

    @Rule
    public final ActivityTestRule<NoteEditActivity> activityTestRule = new ActivityTestRule<>(
        NoteEditActivity.class, true, false);
    private NoteEditFragment noteEditFragment;

    @Before
    public void setUp() throws Exception {
        activityTestRule.launchActivity(new Intent().putExtra(NoteEditActivity.PARAM_NOTE_ID, 2));
        this.noteEditFragment = ((NoteEditFragment) this.activityTestRule.getActivity()
            .getFragmentManager().findFragmentById(R.id.fragment_container));
    }

    @Test
    public void testViewElements() throws PackageManager.NameNotFoundException {
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
            .check(matches(withText(R.string.title_activity_note_edit)));
        onView(withId(R.id.btn_submit)).check(matches(withText(R.string.button_save)));
    }

    @Test
    public void testGetNoteId() {
        assertEquals(2, this.noteEditFragment.getNoteId());
    }

    @Test
    public void testSubmitButtonTextChanged() {
        onView(withText(R.string.button_save)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowNote() {

        this.activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NoteEditActivityTest.this.noteEditFragment.showNote(
                    new NoteEntity("Note title", "Note content..."));
            }
        });

        onView(withText("Note title")).check(matches(isDisplayed()));
        onView(withText("Note content...")).check(matches(isDisplayed()));
    }

    @Test
    public void testEditNote() {

        this.activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NoteEditActivityTest.this.noteEditFragment.showNote(
                    new NoteEntity("Title", "Content"));
            }
        });

        onView(withId(R.id.et_title)).perform(typeText(" updated!"), closeSoftKeyboard());
        onView(withId(R.id.et_content)).perform(typeText(" changed!"));
        onView(withId(R.id.btn_submit)).perform(click());

        verify(this.noteEditFragment.getNoteEditPresenter()).updateNote(
            "Title updated!", "Content changed!");
    }

    @Test
    public void testDeleteItemPressed() {

        onView(withId(R.id.item_delete)).perform(click());

        verify(this.noteEditFragment.getNoteEditPresenter()).deleteNoteButtonPressed();
    }

}
