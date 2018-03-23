package sdk.api.interval;


import sdk.api.BadgeRepo;
import sdk.api.Configuration;

public class Usher {


    private SdkComponent sdkComponent;
    private Configuration configuration;


    private Usher() {
    }

    public static Usher get() {
        return UsherSdkSingletonHolder.instance;
    }

    public static void init(Configuration configuration) {
        get().initialize(configuration);
    }

    private void initialize(Configuration configuration) {
        this.configuration = configuration;
        initializeDaggerInjector();
    }


    public BadgeRepo badgeRepo() {
        return sdkComponent.badgeRepo();
    }

    protected void initializeDaggerInjector() {
        sdkComponent = DaggerSdkComponent.builder().configuration(configuration).build();
    }


    private static class UsherSdkSingletonHolder {
        public static final Usher instance = new Usher();
    }


}
