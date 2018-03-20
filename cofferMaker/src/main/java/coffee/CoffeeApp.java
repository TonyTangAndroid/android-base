package coffee;

public class CoffeeApp {

    public static void main(String[] args) {
        CoffeeShop coffeeShop = DaggerCoffeeShop.builder().build();
        coffeeShop.maker().brew();
    }
}
