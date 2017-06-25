package com.tony_tang.android.demo.common;

import android.app.Activity;

import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivityComponent;
import com.tony_tang.android.demo.feature.note_list.NoteListActivity;
import com.tony_tang.android.demo.feature.note_list.NoteListActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class DemoActivityInjector {

    @Binds
    @IntoMap
    @ActivityKey(NoteListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> injectMainActivity(NoteListActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(NoteDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> injectDetailActivity(NoteDetailActivityComponent.Builder builder);

}
