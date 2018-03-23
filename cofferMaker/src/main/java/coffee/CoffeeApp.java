package coffee;

import javax.inject.Inject;

public class CoffeeApp {


    public CoffeeApp() {
    }

    public CoffeeMaker getCoffeeMaker() {
        return coffeeMaker;
    }

    @Inject
    CoffeeMaker coffeeMaker;

    public static void main(String[] args) {

        CoffeeApp coffeeApp = new CoffeeApp();
        CoffeeShopComponent coffeeShopComponent = DaggerCoffeeShopComponent.builder().build();

        {
//            coffeeShopComponent.inject(coffeeApp);
//            coffeeShopComponent.inject(coffeeApp);
            coffeeShopComponent.inject(coffeeApp);
            coffeeShopComponent.inject(coffeeApp);
            coffeeShopComponent.inject(coffeeApp);
            coffeeApp.getCoffeeMaker().brew();
        }

    }


}
