package com.tony.tang.movie.domain;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RestApiErrorExceptionTest {

    private static final String FAKE_MESSAGE = "Error message";
    private static final int FAKE_STATUS = 100;

    private DomainException restApiErrorException;

    @Before
    public void setup() {
        this.restApiErrorException = new DomainException(FAKE_STATUS, FAKE_MESSAGE);
    }

    @Test
    public void testRestApiErrorConstructor() {
        assertThat(this.restApiErrorException.getMessage(), is(FAKE_MESSAGE));
        assertThat(this.restApiErrorException.getCode(), is(FAKE_STATUS));
    }

    @Test
    public void testStatusCodes() {
        Truth.assertThat(DomainException.NOT_FOUND).isEqualTo(404);
        Truth.assertThat(DomainException.BAD_REQUEST).isEqualTo(400);
        Truth.assertThat(DomainException.UNAUTHORIZED).isEqualTo(401);
        Truth.assertThat(DomainException.UPGRADE_REQUIRED).isEqualTo(426);
        Truth.assertThat(DomainException.UNPROCESSABLE_ENTITY).isEqualTo(422);
        Truth.assertThat(DomainException.INVALID_SESSION_TOKEN).isEqualTo(209);
        Truth.assertThat(DomainException.INTERNAL_SERVER_ERROR).isEqualTo(500);
    }

}
