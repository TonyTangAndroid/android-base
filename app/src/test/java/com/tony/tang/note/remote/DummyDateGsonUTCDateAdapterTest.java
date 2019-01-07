
package com.tony.tang.note.remote;

import com.google.common.truth.Truth;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DummyDateGsonUTCDateAdapterTest {


    @Test
    public void deserialize() throws ParseException {
        DateFormat dateFormat = RemoteModule.GsonModule.dateFormat();
        Date date = dateFormat.parse("2016-12-05T03:07:31.605Z");
        Truth.assertThat(dateFormat.format(date)).isEqualTo("2016-12-05T03:07:31.605Z");
    }



}