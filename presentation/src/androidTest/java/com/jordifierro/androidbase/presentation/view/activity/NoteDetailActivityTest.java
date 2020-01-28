package com.jordifierro.androidbase.presentation.view.activity;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.NoteDetailFragment;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class NoteDetailActivityTest {

    @Rule
    public final ActivityTestRule<NoteDetailActivity> activityTestRule = new ActivityTestRule<>(
        NoteDetailActivity.class, true, false);
    private NoteDetailFragment noteDetailFragment;

    @Before
    public void setUp() throws Exception {
        activityTestRule.launchActivity(new Intent().putExtra(NoteDetailActivity.PARAM_NOTE_ID, 2));
        this.noteDetailFragment = ((NoteDetailFragment) this.activityTestRule.getActivity()
            .getFragmentManager().findFragmentById(R.id.fragment_container));
    }

    @Test
    public void testViewElements() {
        onView(Matchers.allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
            .check(matches(withText(R.string.title_activity_note_detail)));
    }

    @Test
    public void testGetNoteId() {
        assertEquals(2, this.noteDetailFragment.getNoteId());
    }

    @Test
    public void testShowNote() {

        this.activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NoteDetailActivityTest.this.noteDetailFragment.showNote(
                    new NoteEntity("Note title", "Note content..."));
            }
        });

        onView(withText("Note title")).check(matches(isDisplayed()));
        onView(withText("Note content...")).check(matches(isDisplayed()));
    }

    @Test
    public void testEditNotePressed() {
        Intents.init();

        onView(withId(R.id.item_edit)).perform(click());

        intended(allOf(
            hasComponent(NoteEditActivity.class.getName()),
            hasExtra(NoteEditActivity.PARAM_NOTE_ID, 2)));
        Intents.release();
    }

}
