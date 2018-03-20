package com.tony_tang.android.demo.common.module;

import android.content.Context;

import com.google.gson.Gson;
import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.domain.cache.UserCache;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.scope.ApplicationScope;
import com.tony_tang.android.demo.feature.common.EmptyViewEntity;
import com.tony_tang.android.demo.feature.common.FooterViewEntity;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

@DebugLog
@Module
public class DataLocalModule {

    @Provides
    @ApplicationScope
    SessionRepository provideSessionRepository(UserCache userCache, Gson gson) {
        return new SessionDataRepository(userCache, gson);
    }

    @Provides
    @ApplicationScope
    EmptyViewEntity provideEmptyViewEntity(Context applicationContext) {
        EmptyViewEntity build = EmptyViewEntity.builder()
                .showLoading(true)
                .showRetry(false)
                .showImage(false)
                .imageDrawableRes(R.drawable.ic_launcher)
                .middleHint1(applicationContext.getString(R.string.loading))
                .build();
        return build;
    }


    @Provides
    @ApplicationScope
    FooterViewEntity provideFooterViewEntity() {
        return FooterViewEntity.builder()
                .showLoading(true)
                .showFooterView(false).build();
    }

}
