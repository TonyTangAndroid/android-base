package iammert.com.dagger_android_injection.ui.detail.fragment.v2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mertsimsek on 02/06/2017.
 */
@Module
public class NoteDetailFragmentModule {

    @Provides
    NoteDetailFragmentView provideDetailFragmentView(NoteDetailFragment noteDetailFragment){
        return noteDetailFragment;
    }

}
