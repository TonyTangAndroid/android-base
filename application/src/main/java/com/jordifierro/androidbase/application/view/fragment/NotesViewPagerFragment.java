package com.jordifierro.androidbase.application.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jordifierro.androidbase.application.R;
import com.jordifierro.androidbase.application.view.adapter.BadgeViewPagerAdapter;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.presenter.BasePresenter;
import com.jordifierro.androidbase.presentation.presenter.NotesViewPagerPresenter;
import com.jordifierro.androidbase.presentation.view.NotesViewPagerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NotesViewPagerFragment extends CleanFragment implements NotesViewPagerView {

    @Inject
    NotesViewPagerPresenter notesViewPagerPresenter;

    @BindView(R.id.tab_dots)
    TabLayout tabDots;
    @BindView(R.id.view_pager_badge)
    ViewPager viewPagerBadge;


    @Override
    protected void callInjection() {
        this.getFragmentInjector().inject(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_notes_view_pager;
    }

    @Override
    protected BasePresenter presenter() {
        return this.notesViewPagerPresenter;
    }


    @Override
    public void showNotes(List<NoteEntity> notes) {

        BadgeViewPagerAdapter pagerAdapter = new BadgeViewPagerAdapter(getChildFragmentManager(), notes);
        viewPagerBadge.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void initUI() {
        tabDots.setupWithViewPager(viewPagerBadge, true);

    }


}
