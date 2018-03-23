package sdk.api;

import dagger.Binds;
import dagger.Module;

@Module
abstract class BadgeModule {

    @Binds
    abstract BadgeRepo badgeRepo(BadgeRepoImpl badgeRepoImpl);
}
