package com.jordifierro.androidbase.application.view.activity;

import com.jordifierro.androidbase.application.view.activity.base.WebViewActivity;
import com.jordifierro.androidbase.data.net.RestApi;

public class TermsActivity extends WebViewActivity {

    private static final String TERMS_URL = RestApi.URL_BASE + "/terms";

    @Override
    public String getUrl() {
        return TERMS_URL;
    }

}
