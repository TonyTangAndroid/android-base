package com.jordifierro.androidbase.application.dependency.component;

import android.content.Context;

import com.jordifierro.androidbase.application.dependency.ApplicationScope;
import com.jordifierro.androidbase.application.dependency.module.ApplicationModule;
import com.jordifierro.androidbase.application.dependency.module.ContextModule;
import com.jordifierro.androidbase.application.dependency.module.DataModule;
import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    SessionRepository sessionRepository();

    UserRepository userRepository();

    NoteRepository noteRepository();

}
