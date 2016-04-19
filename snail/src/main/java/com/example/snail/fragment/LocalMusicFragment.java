package com.example.snail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snail.R;
import com.example.snail.adapter.LocalMusicAdapter;
import com.example.snail.utils.DisplayTool;

import java.util.ArrayList;

/**
 * Created by chaoming on 2016/3/15.
 */
public class LocalMusicFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ArrayList<Fragment> fragmentList;

    private ViewPager viewPager;

    private TextView tv_song, tv_songer, tv_special;

    private ImageView scrollImage, image_exit;

    private int table_width;

    private LinearLayout lin_null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic, null);
        initList();
        initView( view );
        return view;
    }

    private void initView( View view ) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new LocalMusicAdapter(getChildFragmentManager(), fragmentList));
        viewPager.setOnPageChangeListener(this);

        lin_null = (LinearLayout) view.findViewById(R.id.line_null);
        lin_null.setOnClickListener(this);
        tv_song = (TextView) view.findViewById(R.id.tv_song);
        tv_song.setOnClickListener(this);
        tv_songer = (TextView) view.findViewById(R.id.tv_songer);
        tv_songer.setOnClickListener(this);

        scrollImage = (ImageView) view.findViewById(R.id.image);
        table_width = DisplayTool.getScreenWidth(getActivity()) / 3;
        scrollImage.setMinimumWidth(table_width);

    }

    private void initList() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new SongListFragment());
        fragmentList.add(new SongerListFragment());
        fragmentList.add(new SpecialFragment());
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) scrollImage.getLayoutParams();
        lp.leftMargin = (int) ((position + positionOffset) * table_width);
        scrollImage.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
