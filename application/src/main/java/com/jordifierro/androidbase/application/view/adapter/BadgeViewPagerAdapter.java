package com.jordifierro.androidbase.application.view.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.jordifierro.androidbase.application.view.fragment.NoteDetailFragmentForPager;
import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;


public class BadgeViewPagerAdapter extends FragmentStatePagerAdapter {


    private List<NoteEntity> badgeIdList;

    public BadgeViewPagerAdapter(FragmentManager fm, List<NoteEntity> badgeIdList) {
        super(fm);
        this.badgeIdList = badgeIdList;
    }

    @Override
    public Fragment getItem(int position) {
        return NoteDetailFragmentForPager.newInstance(badgeIdList.get(position).getObjectId());
    }

    @Override
    public int getCount() {
        return badgeIdList == null ? 0 : badgeIdList.size();
    }
}