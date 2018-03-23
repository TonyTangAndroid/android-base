package sdk.api.interval;

import dagger.BindsInstance;
import dagger.Component;
import sdk.api.BadgeRepo;
import sdk.api.Configuration;

@ApplicationScope
@Component(modules = {ConfigureModule.class, BadgeModule.class})
interface SdkComponent {

    BadgeRepo badgeRepo();


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder configuration(Configuration configuration);

        SdkComponent build();
    }

}
