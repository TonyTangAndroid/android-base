package dream;

import javax.inject.Inject;


public class DreamApp {


    public DreamApp() {
    }

    public AppStateDataRepository appStateRepository() {
        return appStateRepository;
    }

    @Inject
    AppStateDataRepository appStateRepository;
    @Inject
    AppStateDataRepository appStateRepository2;

    public static void main(String[] args) {

        DreamApp dreamApp = new DreamApp();
        DreamApp dreamApp2 = new DreamApp();
        {
            DreamComponent coffeeShopComponent = DaggerDreamComponent.builder().build();
            coffeeShopComponent.inject(dreamApp);
            coffeeShopComponent.inject(dreamApp2);
            dreamApp.appStateRepository().appOnForeground();
        }


    }


}
