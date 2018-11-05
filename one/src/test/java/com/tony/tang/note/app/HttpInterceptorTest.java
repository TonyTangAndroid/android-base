package com.tony.tang.note.app;

import com.google.common.truth.Truth;

import org.junit.Test;

import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class HttpInterceptorTest {

    @Test
    public void testHttpInterceptor() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWebServer.enqueue(new MockResponse());

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new HttpInterceptor()).build();
        okHttpClient.newCall(new Request.Builder().url(mockWebServer.url("/")).build()).execute();

        RecordedRequest request = mockWebServer.takeRequest();
        Truth.assertThat(request.getHeader("Accept-Language")).isEqualTo(Locale.getDefault().getLanguage());
        Truth.assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");

        mockWebServer.shutdown();
    }

}
