package com.jordifierro.androidbase.data.net.interceptor;

import com.jordifierro.androidbase.data.net.RestApi;

import org.junit.Test;

import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static junit.framework.Assert.Truth.assertThat;

public class HttpInterceptorTest {

    @Test
    public void testHttpInterceptor() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWebServer.enqueue(new MockResponse());

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new HttpInterceptor()).build();
        okHttpClient.newCall(new Request.Builder().url(mockWebServer.url("/")).build()).execute();

        RecordedRequest request = mockWebServer.takeRequest();
        Truth.assertThat(Locale.getDefault().getLanguage(), request.getHeader("Accept-Language"));
        Truth.assertThat(RestApi.PARSE_APPLICATION_ID_VALUE, request.getHeader(HttpInterceptor.X_PARSE_APPLICATION_ID));
        Truth.assertThat(HttpInterceptor.APPLICATION_JSON, request.getHeader(HttpInterceptor.CONTENT_TYPE));

        mockWebServer.shutdown();
    }

}
