package sdk.api;

import javax.inject.Inject;
import javax.inject.Named;

class BadgeRepoImpl implements BadgeRepo {


    private final String configureUserName;
    private final String configuredPassword;

    @Inject
    public BadgeRepoImpl(@Named("configured_user_name") String configureUserName,
                         @Named("configured_password") String configuredPassword) {

        System.out.println("creating  BadgeRepoImpl");
        this.configureUserName = configureUserName;
        this.configuredPassword = configuredPassword;
    }

    public boolean login(String userName, String password) {
        return configureUserName.equals(userName) && configuredPassword.equals(password);
    }
}
