package coffee;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {DripCoffeeModule.class})
public interface CoffeeShopComponent {

    void inject(CoffeeApp app);

    CoffeeMaker coffeeMaker();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder coffeeApp(CoffeeApp coffeeApp);

        CoffeeShopComponent build();
    }
}