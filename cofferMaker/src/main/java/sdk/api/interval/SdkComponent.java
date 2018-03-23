package sdk.api.interval;

import dagger.BindsInstance;
import dagger.Component;
import sdk.api.Configuration;

@ApplicationScope
@Component(modules = {ConfigureModule.class, BadgeModule.class})
interface SdkComponent {

    void injectUsher(Usher usher);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder configuration(Configuration configuration);

        SdkComponent build();
    }

}
