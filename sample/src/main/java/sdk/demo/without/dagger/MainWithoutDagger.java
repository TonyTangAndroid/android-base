package sdk.demo.without.dagger;

import sdk.api.Configuration;
import sdk.api.interval.Badge;
import sdk.api.interval.Usher;

public class MainWithoutDagger {


    public static void main(String[] args) {
        System.out.println("Demonstrate how to use Usher sdk without dagger");
        Usher.init(new Configuration("tony", "123"));
        new MainWithoutDagger().doIt();
    }

    public void doIt() {
        System.out.println(Badge.login("tony", "123"));
        System.out.println(Badge.login("tonytang", "123"));
        System.out.println(Usher.get().loginBadgeUseCase().login("tonytang", "123"));
        System.out.println(Usher.get().loginBadgeUseCase().login("tonytang", "123"));
    }
}
