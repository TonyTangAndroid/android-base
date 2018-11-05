package com.tony.tang.note.app;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResponseErrorEntityTest {

    private static final int FAKE_STATUS = 100;
    private static final String FAKE_MESSAGE = "Error message";

    private ResponseErrorEntity error;

    @Before
    public void setup() {
        this.error = new ResponseErrorEntity(FAKE_MESSAGE, FAKE_STATUS);
    }

    @Test
    public void tesErrorConstructor() {
        assertThat(this.error.getError(), is(FAKE_MESSAGE));
        assertThat(this.error.getCode(), is(FAKE_STATUS));
    }

    @Test
    public void testNoteSetters() {

        this.error.setError("Another message");
        this.error.setCode(200);

        assertThat(this.error.getError(), is("Another message"));
        assertThat(this.error.getCode(), is(200));
    }

}
