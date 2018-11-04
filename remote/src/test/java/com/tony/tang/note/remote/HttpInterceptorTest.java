package com.tony.tang.note.remote;

import com.google.common.truth.Truth;
import com.tony.tang.note.remote.RestApi;
import com.tony.tang.note.remote.HttpInterceptor;

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
        Truth.assertThat(request.getHeader(HttpInterceptor.X_PARSE_APPLICATION_ID)).isEqualTo(RestApi.PARSE_APPLICATION_ID_VALUE);
        Truth.assertThat(request.getHeader(HttpInterceptor.CONTENT_TYPE)).isEqualTo(HttpInterceptor.APPLICATION_JSON);

        mockWebServer.shutdown();
    }

}
