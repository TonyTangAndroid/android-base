package com.tony_tang.android.demo.common;

import android.app.Application;
import android.content.Context;

import com.tony_tang.android.demo.common.module.SharedPreferenceModule;
import com.tony_tang.android.demo.common.module.ThreadModule;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivityComponent;
import com.tony_tang.android.demo.feature.note_list.NoteListActivityComponent;

import dagger.Module;
import dagger.Provides;

/**

 */
@Module(subcomponents = {
        NoteListActivityComponent.class,
        NoteDetailActivityComponent.class
}, includes = {
        SharedPreferenceModule.class,
        ThreadModule.class
})
public class DemoApplicationModule {

    @Provides
    Context provideContext(Application application) {
        return application;
    }

}
