package com.tony.tang.note.domain.entity;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.github.android.tang.tony.test.util.TestUtil;

public class DateFormatTest {

    @Test
    public void serialize() throws ParseException {

        String json = TestUtil.content("dummy_date.json", this);
        DummyDate dummyDate = gson().fromJson(json, DummyDate.class);
        Date parse = dateFormat().parse("2016-12-05T03:07:31.605Z");
        Truth.assertThat(dummyDate.getCreatedAt()).isEquivalentAccordingToCompareTo(parse);
    }

    @Test
    public void deserialize() throws ParseException {
        Date createdAt = dateFormat().parse("2016-12-05T03:07:31.605Z");
        DummyDate dummyDate = new DummyDate();
        dummyDate.setCreatedAt(createdAt);
        Truth.assertThat(gson().toJson(dummyDate)).isEqualTo(TestUtil.content("dummy_date.json", this));
    }


    private Gson gson() {
        GsonUTCDateAdapter gsonUTCDateAdapter = new GsonUTCDateAdapter(dateFormat());
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Date.class, gsonUTCDateAdapter).create();
    }

    private DateFormat dateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat;
    }

}