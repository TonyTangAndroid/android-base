package sdk.api.interval;


import javax.inject.Inject;

import sdk.api.BadgeRepo;
import sdk.api.Configuration;
import sdk.api.LoginBadgeUseCase;

public class Usher {


    @Inject
    BadgeRepo badgeRepo1;

    @Inject
    BadgeRepo badgeRepo2;


    @Inject
    LoginBadgeUseCase loginBadgeUseCase1;

    @Inject
    LoginBadgeUseCase loginBadgeUseCase2;


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
        return badgeRepo1;
    }

    public LoginBadgeUseCase loginBadgeUseCase1() {
        return loginBadgeUseCase1;
    }

    protected void initializeDaggerInjector() {
        DaggerSdkComponent.builder().configuration(configuration).build().injectUsher(this);
    }


    private static class UsherSdkSingletonHolder {
        public static final Usher instance = new Usher();
    }


}
