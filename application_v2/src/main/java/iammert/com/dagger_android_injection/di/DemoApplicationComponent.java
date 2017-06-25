package iammert.com.dagger_android_injection.di;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import iammert.com.dagger_android_injection.DemoApplication;

/**
 * Created by mertsimsek on 25/05/2017.
 */
@Component(modules = {
        AndroidInjectionModule.class,
        DemoApplicationModule.class,
        DemoActivityInjector.class})
public interface DemoApplicationComponent {

    void inject(DemoApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        DemoApplicationComponent build();
    }
}
