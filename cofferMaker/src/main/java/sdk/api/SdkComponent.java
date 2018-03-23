package sdk.api;

import dagger.BindsInstance;
import dagger.Component;

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
