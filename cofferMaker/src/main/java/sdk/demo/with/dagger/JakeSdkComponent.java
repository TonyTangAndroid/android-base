package sdk.demo.with.dagger;

import dagger.BindsInstance;
import dagger.Component;
import sdk.api.interval.Usher;

//@ApplicationScope
@Component(modules = {UsherModule.class, JakeBadgeModule.class})
interface JakeSdkComponent {

    void inject(AppSampleWithDaggerInjectUsherInstance mySdkSample);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder usher(Usher usher);

        JakeSdkComponent build();
    }

}
