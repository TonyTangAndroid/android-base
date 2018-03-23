package sdk.demo.without.dagger;

import sdk.api.Configuration;
import sdk.api.interval.Badge;
import sdk.api.interval.Usher;

public class AppSampleWithoutDagger {


    public static void main(String[] args) {
        System.out.println("Demonstrate how to use Usher sdk without dagger");


        Usher.init(new Configuration("tony", "123"));
        new AppSampleWithoutDagger().doIt();
    }

    public void doIt() {


        System.out.println(Badge.login("tony", "123"));
        System.out.println(Badge.login("tonytang", "123"));
        System.out.println(Usher.get().loginBadgeUseCase1().login("tonytang", "123"));
        System.out.println(Usher.get().loginBadgeUseCase1().login("tonytang", "123"));
    }
}
