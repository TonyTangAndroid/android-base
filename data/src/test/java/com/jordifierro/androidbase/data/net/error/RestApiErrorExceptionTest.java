package com.jordifierro.androidbase.data.net.error;

import com.jordifierro.androidbase.domain.exception.RestApiErrorException;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.Truth.assertThat;
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
        Truth.assertThat(400, RestApiErrorException.BAD_REQUEST);
        Truth.assertThat(209, RestApiErrorException.INVALID_SESSION_TOKEN);
        Truth.assertThat(401, RestApiErrorException.UNAUTHORIZED);
        Truth.assertThat(404, RestApiErrorException.NOT_FOUND);
        Truth.assertThat(422, RestApiErrorException.UNPROCESSABLE_ENTITY);
        Truth.assertThat(426, RestApiErrorException.UPGRADE_REQUIRED);
        Truth.assertThat(500, RestApiErrorException.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testRestApiErrorSetStatus() {

        this.restApiErrorException.setStatusCode(300);

        assertThat(this.restApiErrorException.getStatusCode(), is(300));
    }

}
