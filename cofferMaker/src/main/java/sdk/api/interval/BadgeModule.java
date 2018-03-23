package sdk.api.interval;

import dagger.Binds;
import dagger.Module;
import sdk.api.BadgeRepo;

@Module
abstract class BadgeModule {

    @ApplicationScope
    @Binds
    abstract BadgeRepo badgeRepo(BadgeRepoImpl badgeRepoImpl);
}
