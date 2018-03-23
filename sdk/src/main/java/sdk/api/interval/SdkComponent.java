package sdk.api.interval;

import dagger.BindsInstance;
import dagger.Component;
import sdk.api.BadgeRepo;
import sdk.api.Configuration;

@SdkScope
@Component(modules = {ConfigureModule.class, BadgeModule.class})
public interface SdkComponent {

    void injectUsher(Usher usher);

    BadgeRepo badgeRepo();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder configuration(Configuration configuration);

        SdkComponent build();
    }

}
