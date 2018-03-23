package coffee;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
abstract class PumpModule {

  @Singleton
  @Binds
  abstract Pump providePump(Thermosiphon pump);
}
