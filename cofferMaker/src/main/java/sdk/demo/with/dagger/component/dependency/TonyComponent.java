package sdk.demo.with.dagger.component.dependency;

import dagger.Component;
import sdk.api.interval.SdkComponent;

//@ApplicationScope
@Component(modules = {TonyBadgeModule.class}, dependencies = SdkComponent.class)
interface TonyComponent {

    void inject(AppSampleWithDaggerInjectByComponentDependency app);

    @Component.Builder
    interface Builder {

        TonyComponent build();

        Builder setSdkComponent(SdkComponent sdkComponent);
    }

}
