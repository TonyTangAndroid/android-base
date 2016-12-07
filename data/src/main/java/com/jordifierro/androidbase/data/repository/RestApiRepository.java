package com.jordifierro.androidbase.data.repository;

import com.google.gson.Gson;
import com.jordifierro.androidbase.data.net.error.RestApiErrorException;
import com.jordifierro.androidbase.data.net.wrapper.ResponseErrorWrapper;

import java.io.IOException;

import retrofit2.Response;

public abstract class RestApiRepository {

	protected void handleResponseError(Response response) {
		if (!response.isSuccess()) {

			try {
				ResponseErrorWrapper errorWrapper;
				final String errorBody = response.errorBody().string();
				System.out.println("response.code:" + response.code() + "\nresponse.message:" + response.message() + "\nresponse.errorBody:" + errorBody);
				errorWrapper = new Gson().fromJson(errorBody, ResponseErrorWrapper.class);
				throw new RestApiErrorException(errorWrapper.getError(), errorWrapper.getCode());
			} catch (IOException | NullPointerException e) {
				throw new RestApiErrorException(response.message(), response.code());
			}
		}
	}
}
