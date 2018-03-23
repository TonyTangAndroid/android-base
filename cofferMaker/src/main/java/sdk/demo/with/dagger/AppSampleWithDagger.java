package sdk.demo.with.dagger;

import javax.inject.Inject;

import sdk.api.Configuration;
import sdk.api.interval.Usher;

public class AppSampleWithDagger {


    @Inject
    MyLoginBadgeUseCase myLoginBadgeUseCase;
    @Inject
    MyBadgeRepo myBadgeRepo;

    @Inject
    MyLoginBadgeUseCase myLoginBadgeUseCase1;
    @Inject
    MyBadgeRepo myBadgeRepo1;


    public static void main(String[] args) {
        System.out.println();
        System.out.println("Demonstrate how to use Usher sdk with dagger by inject Usher instance");
        System.out.println();
        System.out.println();
        System.out.println();

        Usher.init(new Configuration("tony", "123"));
        System.out.println("Usher sdk initialized");
        System.out.println();
        System.out.println();
        new AppSampleWithDagger().doIt();
    }

    public void doIt() {

        DaggerMySdkComponent.builder().usher(Usher.get()).build().inject(this);
        System.out.println("App initialized");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(myLoginBadgeUseCase.login("tony", "123"));
        System.out.println(myLoginBadgeUseCase1.login("tony", "123"));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(myBadgeRepo.login("tonytang", "123"));
        System.out.println(myBadgeRepo1.login("tonytang", "123"));
    }
}
