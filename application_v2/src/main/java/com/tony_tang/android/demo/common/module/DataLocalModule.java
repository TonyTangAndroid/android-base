package com.tony_tang.android.demo.common.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.common.scope.ApplicationScope;
import com.tony_tang.android.demo.feature.common.EmptyViewEntity;
import com.tony_tang.android.demo.feature.common.FooterViewEntity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
//@DebugLog
@Module
public class DataLocalModule {

    //重点4:
    //1,不能提供重复的类型。
    //2,如果提供重复的类型，需要使用Named来表明 它们的区别。
    //3,使用的地方如果没有使用named 那么它会自动引用默认的类型。
    //4,如果使用的地方使用没有注册named，Dagger 会报错。
    //String, Boolean 都建议使用named来区别它。
    //@DebugLog
    @Named("session_from_mini_boss_1")
    @Provides
    @ApplicationScope
    SessionRepository provideSessionRepositoryMiniBoss(SharedPreferences sharedPreferences) {
        SessionDataRepository sessionDataRepository = new SessionDataRepository(sharedPreferences);
        return sessionDataRepository;
    }


    //@DebugLog
    @Named("sdsdsds")
    @Provides
    @ApplicationScope
    String provideString(@Named("session_from_mini_boss_1")
                                 SessionRepository sessionRepository) {

        return sessionRepository.toString();
    }


    /**
     * 重点三：Dagger 会自动 寻找 依赖，
     * 只要这个 依赖被小boss 提供了，Dagger
     * 这个大Boss就可以分配(共享)这个依赖。
     */
    //1 默认
    @Provides
    @ApplicationScope
//4308
    SessionRepository provideSessionRepository(SharedPreferences sharedPreferences) {
        SessionDataRepository sessionDataRepository = new SessionDataRepository(sharedPreferences);
        return sessionDataRepository;
    }


    //重点二
    //Provides 这个Annotation 不能和Binds这个Annotation
    //同时存在同一个Module。

    //1,必须是具体的实现方法。
    //2,可以传递无限个参数。
    //3,必须自己去实现对应的返回结果。
    @Provides
    @ApplicationScope
    EmptyViewEntity provideEmptyViewEntity
    (Context applicationContext,
     @Named("sdsdsds") String testString) {
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
