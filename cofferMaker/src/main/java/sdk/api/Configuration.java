package sdk.api;

public class Configuration {

    private final String configuredUserName;
    private final String configuredPassword;

    public Configuration(String configuredUserName, String configuredPassword) {

        this.configuredUserName = configuredUserName;
        this.configuredPassword = configuredPassword;
    }

    public String getConfiguredUserName() {
        return configuredUserName;
    }

    public String getConfiguredPassword() {
        return configuredPassword;
    }
}
