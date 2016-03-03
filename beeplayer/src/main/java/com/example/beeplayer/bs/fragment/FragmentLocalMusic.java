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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.adapter.LocalMusicAdapter;
import com.example.beeplayer.bs.tools.MyTools;

import java.util.ArrayList;


/**
 * 本地音乐界面 包含 3个fragment 歌曲列表 歌手列表 专辑列表
 *
 * @author Administrator
 *
 */
public class FragmentLocalMusic extends Fragment implements OnClickListener, OnPageChangeListener {

	private TextView tv_song, tv_songer, tv_special;
	private ImageView image, image_exit;
	private int table_width;
	private LinearLayout lin_null;
	private ArrayList<Fragment> fragments_list;
	private ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_locamusic, null);
		initList();
		initView(view);
		return view;
	}

	private void initList() {
		fragments_list = new ArrayList<Fragment>();
		fragments_list.add(new SongListFragment());
		fragments_list.add(new SongerListFragment());
		fragments_list.add(new SpecialListFragment());
	}

	private void initView(View view) {
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		viewPager.setAdapter(new LocalMusicAdapter(getChildFragmentManager(), fragments_list));
		viewPager.setOnPageChangeListener(this);
		image_exit = (ImageView) view.findViewById(R.id.image_exit);
		image_exit.setOnClickListener(this);
		lin_null = (LinearLayout) view.findViewById(R.id.lin_null);
		lin_null.setOnClickListener(this);
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.image_exit:
				getActivity().getSupportFragmentManager().popBackStack();
				break;
			case R.id.tv_song:
				viewPager.setCurrentItem(0);
				break;
			case R.id.tv_songer:
				viewPager.setCurrentItem(1);
				break;
			case R.id.tv_special:
				viewPager.setCurrentItem(3);
				break;

			default:
				break;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams lp = (LayoutParams) image.getLayoutParams();
		lp.leftMargin = (int) ((arg0 + arg1) * table_width);
		image.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

}
