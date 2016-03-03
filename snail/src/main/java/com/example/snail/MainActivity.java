package com.example.snail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snail.adapter.SnailPagerAdapter;
import com.example.snail.fragment.FindFragment;
import com.example.snail.fragment.MineFragment;
import com.example.snail.utils.DisplayTool;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SlidingFragmentActivity implements ViewPager.OnPageChangeListener {
    private List<Fragment> fragmentList;
    private ViewPager viewPager;
    private TextView textMine;

    private ImageView slideImage;

    private int tableWidth;

    private MineFragment mineFragment;

    private FindFragment findFragment;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView( R.layout.activity_main );

        initView( );

        initSlidingMenu();

    }

    private void initSlidingMenu() {
        setBehindContentView(R.layout.layout_leftmenu);
    }

    private void initView( ) {
        fragmentList = new ArrayList<Fragment>();
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        slideImage = (ImageView) findViewById(R.id.img_slide);

        tableWidth = DisplayTool.getScreenWidth(this) / 4;
        slideImage.setMinimumWidth(tableWidth);

        mineFragment = new MineFragment();
        fragmentList.add(mineFragment);

        findFragment = new FindFragment();
        fragmentList.add(findFragment);

        fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new SnailPagerAdapter(fragmentManager, fragmentList));
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) slideImage.getLayoutParams();
        lp.leftMargin = (int) ((position + positionOffset) * tableWidth);
        slideImage.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
