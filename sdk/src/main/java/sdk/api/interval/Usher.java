package sdk.api.interval;


import javax.inject.Inject;

import sdk.api.BadgeRepo;
import sdk.api.Configuration;
import sdk.api.LoginBadgeUseCase;

public class Usher {


    @Inject
    BadgeRepo badgeRepo;


    @Inject
    LoginBadgeUseCase loginBadgeUseCase;


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
        if (this.configuration != null) {
            throw new RuntimeException("It has been initialized");
        }
        this.configuration = configuration;
        initializeDaggerInjector();
    }


    public BadgeRepo badgeRepo() {
        return badgeRepo;
    }

    public LoginBadgeUseCase loginBadgeUseCase() {
        return loginBadgeUseCase;
    }

    protected void initializeDaggerInjector() {
        DaggerSdkComponent.builder().configuration(configuration).build().injectUsher(this);
    }


    private static class UsherSdkSingletonHolder {
        public static final Usher instance = new Usher();
    }


}
