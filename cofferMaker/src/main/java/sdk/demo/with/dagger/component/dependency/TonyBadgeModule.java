package sdk.demo.with.dagger.component.dependency;

import dagger.Module;
import dagger.Provides;
import sdk.api.BadgeRepo;
import sdk.api.interval.ApplicationScope;

@Module
class TonyBadgeModule {

    //@ApplicationScope
    @Provides
    TonyBadgeRepo badgeRepo(BadgeRepo badgeRepo) {
        return new TonyBadgeRepo(badgeRepo);
    }
}
