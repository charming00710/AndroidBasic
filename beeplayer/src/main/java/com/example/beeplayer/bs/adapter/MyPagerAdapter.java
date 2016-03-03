package com.example.beeplayer.bs.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {
	 ArrayList<Fragment> fragmentList;
	public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
		super(fm);
		this.fragmentList =fragmentList;
	}
	@Override
	public Fragment getItem(int position) {

		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}
	
}
