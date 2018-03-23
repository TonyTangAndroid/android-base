package sdk.demo.with.dagger;

import dagger.BindsInstance;
import dagger.Component;
import sdk.api.interval.ApplicationScope;
import sdk.api.interval.Usher;

@ApplicationScope
@Component(modules = {UsherModule.class, MyBadgeModule.class})
interface MySdkComponent {

    void inject(AppSampleWithDagger mySdkSample);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder usher(Usher usher);

        MySdkComponent build();
    }

}
