package iammert.com.dagger_android_injection.ui.detail.activity;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import iammert.com.dagger_android_injection.ui.detail.fragment.v2.NoteDetailFragment;
import iammert.com.dagger_android_injection.ui.detail.fragment.v2.NoteDetailFragmentComponent;

/**
 * Created by mertsimsek on 02/06/2017.
 * <p>
 * NoteDetailFragmentComponent.class,
 */
@Module(subcomponents = {NoteDetailFragmentComponent.class})
public abstract class NoteDetailFragmentProvider {

    @Binds
    @IntoMap
    @FragmentKey(NoteDetailFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideNoteDetailFragmentFactory(NoteDetailFragmentComponent.Builder builder);
}
