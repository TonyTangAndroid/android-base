package com.jordifierro.androidbase.data.net.interceptor;

import com.jordifierro.androidbase.data.net.RestApi;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class HttpInterceptor implements Interceptor {


	@Inject
	public HttpInterceptor() {
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request().newBuilder()
				.addHeader("Accept-Language", Locale.getDefault().getLanguage())
				.addHeader("X-Parse-Application-Id", RestApi.PARSE_APPLICATION_ID_VALUE)
				.addHeader("X-Parse-REST-API-Key", RestApi.PARSE_REST_API_VALUE)
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Parse-Revocable-Session", "1")
				.build();
		return chain.proceed(request);
	}

}
