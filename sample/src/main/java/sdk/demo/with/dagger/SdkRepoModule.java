package sdk.demo.with.dagger;

import dagger.Module;
import dagger.Provides;
import sdk.api.BadgeRepo;
import sdk.api.interval.Usher;

@Module
class SdkRepoModule {

    @Provides
    BadgeRepo badgeRepo(Usher usher) {
        return usher.badgeRepo();
    }
}
