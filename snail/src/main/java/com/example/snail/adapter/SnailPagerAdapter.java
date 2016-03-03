package com.example.snail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chaoming on 2016/3/3.
 */
public class SnailPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public SnailPagerAdapter(FragmentManager fm, List<Fragment> fragmentList ) {
        super(fm);
        this.fragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }
}
