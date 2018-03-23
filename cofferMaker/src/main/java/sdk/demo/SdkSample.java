package sdk.demo;

import sdk.api.Configuration;
import sdk.api.interval.Badge;
import sdk.api.interval.Usher;

public class SdkSample {


    public static void main(String[] args) {
        Usher.init(new Configuration("tony", "123"));
        new SdkSample().doIt();
    }

    public void doIt() {

        System.out.println(Badge.login("tony", "123"));
        System.out.println(Badge.login("tonytang", "123"));
    }
}
