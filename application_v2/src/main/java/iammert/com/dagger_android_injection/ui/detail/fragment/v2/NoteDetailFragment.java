package iammert.com.dagger_android_injection.ui.detail.fragment.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import iammert.com.dagger_android_injection.R;
import iammert.com.dagger_android_injection.ui.detail.fragment.BaseFragment;

/**
 * Created by mertsimsek on 02/06/2017.
 */

public class NoteDetailFragment extends BaseFragment implements NoteDetailFragmentView {


    TextView tvTitle;

    @Inject
    NoteDetailFragmentPresenter detailFragmentPresenter;

    public static NoteDetailFragment newInstance() {
        Bundle args = new Bundle();
        NoteDetailFragment fragment = new NoteDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentPresenter.loadData();
    }

    @Override
    public void onDetailFragmentLoaded(String title) {
        tvTitle.setText(title);
    }
}
