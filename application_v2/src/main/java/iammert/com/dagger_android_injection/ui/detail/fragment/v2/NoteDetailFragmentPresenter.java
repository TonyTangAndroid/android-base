package iammert.com.dagger_android_injection.ui.detail.fragment.v2;

import javax.inject.Inject;

/**
 * Created by mertsimsek on 02/06/2017.
 */

public class NoteDetailFragmentPresenter {

    NoteDetailFragmentView detailFragmentView;

    @Inject
    public NoteDetailFragmentPresenter(NoteDetailFragmentView detailFragmentView) {
        this.detailFragmentView = detailFragmentView;

    }

    public void loadData() {
        detailFragmentView.onDetailFragmentLoaded("I am Tony");
    }
}
