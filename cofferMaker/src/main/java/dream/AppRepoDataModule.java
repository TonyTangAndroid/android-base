package dream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppRepoDataModule {

    @Provides
    @Singleton
    public AppStateDataRepository provideAppRepo() {
        return new AppStateDataRepository();
    }


}
