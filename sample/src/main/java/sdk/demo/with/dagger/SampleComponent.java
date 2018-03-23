package sdk.demo.with.dagger;

import dagger.BindsInstance;
import dagger.Component;
import sdk.api.interval.Usher;
@SampleScope
@Component(modules = {SdkRepoModule.class, SampleBadgeModule.class})
interface SampleComponent {

    void inject(Main main);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder usher(Usher usher);

        SampleComponent build();
    }

}
