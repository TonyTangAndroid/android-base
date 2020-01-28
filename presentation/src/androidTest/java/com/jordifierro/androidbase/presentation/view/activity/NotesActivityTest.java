package com.jordifierro.androidbase.presentation.view.activity;

import android.content.pm.PackageManager;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.fragment.NotesFragment;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class NotesActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);
    private NotesFragment notesFragment;
    private List<NoteEntity> notes = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        this.notesFragment = ((NotesFragment) this.activityTestRule.getActivity()
                                .getFragmentManager().findFragmentById(R.id.fragment_container));
        this.notes.add(new NoteEntity(1, "First title", "First content"));
        this.notes.add(new NoteEntity(2, "Second title", "Second content"));
        this.notes.add(new NoteEntity(3, "Third title", "Third content"));
    }

    @Test
    public void testViewElements() throws PackageManager.NameNotFoundException {
        onView(Matchers.allOf(isAssignableFrom(TextView.class),withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(R.string.title_activity_main)));
        onView(withId(R.id.btn_create_new_note))
                .check(matches(withText(R.string.button_create_new_note)));
    }

    @Test
    public void testShowNotes() {

        this.activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotesActivityTest.this.notesFragment.showNotes(NotesActivityTest.this.notes);
            }
        });

        onView(withText("First title")).check(matches(isDisplayed()));
        onView(withText("Second title")).check(matches(isDisplayed()));
        onView(withText("Third title")).check(matches(isDisplayed()));
    }

    @Test
    public void testNoteSelected() {
        Intents.init();
        this.activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotesActivityTest.this.notesFragment.showNotes(NotesActivityTest.this.notes);
            }
        });

        onView(withText("Second title")).perform(click());

        intended(allOf(
                hasComponent(NoteDetailActivity.class.getName()),
                hasExtra(NoteDetailActivity.PARAM_NOTE_ID, 2)));
        Intents.release();
    }

    @Test
    public void testNavigateToCreateNote() {
        Intents.init();

        onView(withId(R.id.btn_create_new_note)).perform(click());

        intended(hasComponent(NoteCreateActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testNavigateToSettings() {
        Intents.init();

        onView(withId(R.id.item_settings)).perform(click());

        intended(hasComponent(SettingsActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testShowExpirationDate() {

        this.activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotesActivityTest.this.notesFragment.showExpirationWarning();
            }
        });

        String expiration = this.activityTestRule.getActivity()
                .getResources().getString(R.string.message_expiration);
        String update = this.activityTestRule.getActivity()
                .getResources().getString(R.string.message_update);

        onView(withText(containsString(expiration))).inRoot(
                withDecorView(not(this.activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        onView(withText(containsString(update))).inRoot(
                withDecorView(not(this.activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

}
