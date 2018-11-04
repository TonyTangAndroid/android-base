package com.tony.tang.note.domain.entity;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class UserEntityTest {

    @Test
    public void testUserConstructor() throws IOException {

        String json = com.tony.tang.note.domain.entity.TestUtils.json("user_me_ok.json", this);
        UserEntity userEntity = UserEntity.typeAdapter(new Gson()).fromJson(json);
        assertThat(userEntity.email()).isEqualTo("tangzhilu@gmail.com");
        assertThat(userEntity.sessionToken()).isEqualTo("r:LhHHilHJ4tgD7762f5SG19mmd");
    }

}
