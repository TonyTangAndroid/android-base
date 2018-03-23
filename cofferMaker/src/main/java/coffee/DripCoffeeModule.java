package coffee;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

@Module(includes = PumpModule.class)
class DripCoffeeModule {
    @Provides
    @Singleton
    Heater provideHeater() {
        return new ElectricHeater();
    }

    @Provides
    @Singleton
    CoffeeMaker coffeeMaker(Lazy<Heater> heater, Pump pump) {
        return new CoffeeMaker(heater, pump);
    }
}
