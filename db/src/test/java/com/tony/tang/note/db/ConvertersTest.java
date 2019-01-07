package com.tony.tang.note.db;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tony.tang.note.domain.entity.GsonUTCDateAdapter;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.github.android.tang.tony.test.util.TestUtil;

public class ConvertersTest {


    @Test
    public void fromTimestamp() {

        Date date = gson().fromJson(json(), SampleDate.class).getDate();
        long timestamp = Converters.dateToTimestamp(date);
        Truth.assertThat(timestamp).isEqualTo(1546800482605L);
    }

    private String json() {
        return TestUtil.content("date_sample.json", this);
    }

    @Test
    public void dateToTimestamp() {
        Date date = Converters.fromTimestamp(1546800482605L);
        Truth.assertThat(gson().toJson(new SampleDate(date))).isEqualTo(json());
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