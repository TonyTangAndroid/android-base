package sdk.demo.with.dagger;

import dagger.Module;
import dagger.Provides;
import sdk.api.BadgeRepo;
import sdk.api.interval.ApplicationScope;

@Module
class JakeBadgeModule {

    //@ApplicationScope
    @Provides
    JakeBadgeRepo badgeRepo(BadgeRepo badgeRepo) {
        return new JakeBadgeRepo(badgeRepo);
    }
}
