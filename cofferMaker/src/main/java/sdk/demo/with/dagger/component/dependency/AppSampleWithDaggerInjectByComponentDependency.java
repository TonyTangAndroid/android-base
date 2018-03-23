package sdk.demo.with.dagger.component.dependency;

import javax.inject.Inject;

import sdk.api.Configuration;
import sdk.api.interval.DaggerSdkComponent;
import sdk.api.interval.SdkComponent;

public class AppSampleWithDaggerInjectByComponentDependency {


    @Inject
    TonyBadgeUseCase myLoginBadgeUseCase;
    @Inject
    TonyBadgeRepo myBadgeRepo;

    @Inject
    TonyBadgeUseCase myLoginBadgeUseCase1;
    @Inject
    TonyBadgeRepo myBadgeRepo1;


    public static void main(String[] args) {
        System.out.println();
        System.out.println("Demonstrate how to use Usher sdk with dagger by inject Usher instance");
        System.out.println();
        System.out.println();
        System.out.println();


        new AppSampleWithDaggerInjectByComponentDependency().doIt();
    }

    public void doIt() {

        Configuration configuration = new Configuration("tony", "123");
        SdkComponent sdkComponent = DaggerSdkComponent.builder().configuration(configuration).build();
        TonyComponent tonyComponent = DaggerTonyComponent.builder().setSdkComponent(sdkComponent).build();
        tonyComponent.inject(this);


        System.out.println("Tony App initialized");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(myLoginBadgeUseCase.login("tony", "123"));
        System.out.println(myLoginBadgeUseCase1.loginTony("tony", "123"));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(myBadgeRepo.login("tonytang", "123"));
        System.out.println(myBadgeRepo1.login("tonytang", "123"));
    }
}
