package com.example.beeplayer.bs.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.adapter.SearchShowAdapter;
import com.example.beeplayer.bs.tools.MyTools;

import java.util.ArrayList;

public class SearchShowFragment extends Fragment implements OnPageChangeListener, OnClickListener {
	private TextView tv_song, tv_songer, tv_special;
	private ImageView image;
	private int table_width;
	private ArrayList<Fragment> fragment_List;
	private ViewPager viewPager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_search_show, null);
		initList();
		initView(view);
		return view;
	}
	public void initList(){
		fragment_List = new ArrayList<Fragment>();
		fragment_List.add(new SongNetListFragment());
		fragment_List.add(new SongNetListFragment());
		fragment_List.add(new SongNetListFragment());
	}
	public void initView(View view){
		viewPager = (ViewPager) view.findViewById(R.id.pager_serch);
		viewPager.setAdapter(new SearchShowAdapter(getChildFragmentManager(), fragment_List));
		viewPager.setOnPageChangeListener(this);
		tv_song = (TextView) view.findViewById(R.id.tv_song);
		tv_song.setOnClickListener(this);
		tv_songer = (TextView) view.findViewById(R.id.tv_songer);
		tv_songer.setOnClickListener(this);
		tv_special = (TextView) view.findViewById(R.id.tv_special);
		tv_special.setOnClickListener(this);
		image = (ImageView) view.findViewById(R.id.image);
		table_width = MyTools.getScreenWidth(getActivity()) / 3;
		image.setMinimumWidth(table_width);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		LayoutParams lp = (LayoutParams) image.getLayoutParams();
		lp.leftMargin = (int) ((arg0 + arg1) * table_width);
		image.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_song:
			viewPager.setCurrentItem(0);
			break;
		case R.id.tv_songer:
			viewPager.setCurrentItem(1);
			break;
		case R.id.tv_special:
			viewPager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}
}
