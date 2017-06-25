package iammert.com.dagger_android_injection.ui.detail.fragment.v2;

/**
 * Created by mertsimsek on 02/06/2017.
 */

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = NoteDetailFragmentModule.class)
public interface NoteDetailFragmentComponent extends AndroidInjector<NoteDetailFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NoteDetailFragment> {
    }
}
