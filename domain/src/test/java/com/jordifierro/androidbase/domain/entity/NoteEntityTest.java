package com.jordifierro.androidbase.domain.entity;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NoteEntityTest {

    private static final String FAKE_ID = "BLlerFMbhA";
    private static final String FAKE_TITLE = "email@test.com";
    private static final String FAKE_CONTENT = "1234ABCD";

    private NoteEntity note;

    @Before
    public void setup() {
        this.note =  new NoteEntity(FAKE_ID, FAKE_TITLE, FAKE_CONTENT);
    }

    @Test
    public void tesNoteConstructor() {
        assertThat(this.note.getObjectId(), is(FAKE_ID));
        assertThat(this.note.getTitle(), is(FAKE_TITLE));
        assertThat(this.note.getContent(), is(FAKE_CONTENT));
    }

    @Test
    public void testNoteSetters() {
        this.note.setObjectId("BLlerFMbhB");
        this.note.setTitle("AnotherTitle");
        this.note.setContent("AnotherContent");

        assertThat(this.note.getObjectId(), is("BLlerFMbhB"));
        assertThat(this.note.getTitle(), is("AnotherTitle"));
        assertThat(this.note.getContent(), is("AnotherContent"));
    }
}
