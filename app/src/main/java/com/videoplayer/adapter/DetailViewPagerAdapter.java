package com.videoplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.videoplayer.helper.BLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ketan on 3/18/17.
 */

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String LOG_TAG = "DetailViewPagerAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> titleList = new ArrayList<String>();

    public DetailViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}