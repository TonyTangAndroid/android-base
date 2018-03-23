package sdk.api.interval;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import sdk.api.Configuration;

@Module
class ConfigureModule {

    @Named("configured_user_name")
    @Provides
    String configuredUserName(Configuration configuration) {
        return configuration.getConfiguredUserName();
    }


    @Named("configured_password")
    @Provides
    String configuredPassword(Configuration configuration) {
        return configuration.getConfiguredPassword();
    }

}
