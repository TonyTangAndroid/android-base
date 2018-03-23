package sdk.demo;

import sdk.api.BadgeRepo;
import sdk.api.Configuration;
import sdk.api.Usher;

public class SdkSample {

    public static void main(String[] args) {
        Usher.init(new Configuration("tony", "123"));

        BadgeRepo badgeRepo1 = Usher.get().badgeRepo();
        BadgeRepo badgeRepo2 = Usher.get().badgeRepo();

        System.out.println(badgeRepo1.login("tony", "123"));
        System.out.println(badgeRepo2.login("tonytang", "123"));
    }
}
