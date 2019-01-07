package com.tony.tang.note.ui.feature.note.list;

import com.tony.tang.note.app.AppScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import hugo.weaving.DebugLog;

@DebugLog
@Module
public class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(NoteBeanAndroidViewModel.class)
    ViewModel noteBeanAndroidViewModel(Provider<NoteBeanAndroidViewModel> liveDataProvider) {
        return liveDataProvider.get();
    }

    @AppScope
    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

}