package sdk.demo.with.dagger;

import dagger.Module;
import dagger.Provides;
import sdk.api.BadgeRepo;

@Module
class SampleBadgeModule {

    @SampleScope
    @Provides
    SampleBadgeRepo badgeRepo(BadgeRepo badgeRepo) {
        return new SampleBadgeRepo(badgeRepo);
    }
}
