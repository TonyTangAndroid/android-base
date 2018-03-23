package sdk.demo.with.dagger;

import dagger.Module;
import dagger.Provides;
import sdk.api.BadgeRepo;
import sdk.api.interval.ApplicationScope;

@Module
class MyBadgeModule {

    @ApplicationScope
    @Provides
    MyBadgeRepo badgeRepo(BadgeRepo badgeRepo) {
        return new MyBadgeRepo(badgeRepo);
    }
}
