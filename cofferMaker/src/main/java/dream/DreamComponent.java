package dream;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppRepoDataModule.class})
public interface DreamComponent {

    void inject(DreamApp app);

    AppStateDataRepository appStateDataRepository();

    @Component.Builder
    interface Builder {

//        @BindsInstance
//        Builder dreamApp(DreamApp dreamApp);

        DreamComponent build();
    }
}