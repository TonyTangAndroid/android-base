package com.tony.tang.movie;

import com.google.common.truth.Truth;
import com.tony.tang.movie.RestApiErrorException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RestApiErrorExceptionTest {

    private static final String FAKE_MESSAGE = "Error message";
    private static final int FAKE_STATUS = 100;

    private RestApiErrorException restApiErrorException;

    @Before
    public void setup() {
        this.restApiErrorException = new RestApiErrorException(FAKE_MESSAGE, FAKE_STATUS);
    }

    @Test
    public void testRestApiErrorConstructor() {
        assertThat(this.restApiErrorException.getMessage(), is(FAKE_MESSAGE));
        assertThat(this.restApiErrorException.getStatusCode(), is(FAKE_STATUS));
    }

    @Test
    public void testStatusCodes() {
        Truth.assertThat(RestApiErrorException.NOT_FOUND).isEqualTo(404);
        Truth.assertThat(RestApiErrorException.BAD_REQUEST).isEqualTo(400);
        Truth.assertThat(RestApiErrorException.UNAUTHORIZED).isEqualTo(401);
        Truth.assertThat(RestApiErrorException.UPGRADE_REQUIRED).isEqualTo(426);
        Truth.assertThat(RestApiErrorException.UNPROCESSABLE_ENTITY).isEqualTo(422);
        Truth.assertThat(RestApiErrorException.INVALID_SESSION_TOKEN).isEqualTo(209);
        Truth.assertThat(RestApiErrorException.INTERNAL_SERVER_ERROR).isEqualTo(500);
    }

    @Test
    public void testRestApiErrorSetStatus() {
        this.restApiErrorException.setStatusCode(300);
        assertThat(this.restApiErrorException.getStatusCode(), is(300));
    }

}
