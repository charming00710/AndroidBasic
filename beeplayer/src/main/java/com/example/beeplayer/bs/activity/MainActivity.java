package com.example.beeplayer.bs.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.adapter.MyPagerAdapter;
import com.example.beeplayer.bs.fragment.FindFragment;
import com.example.beeplayer.bs.fragment.LeftMenu;
import com.example.beeplayer.bs.fragment.MineFragment;
import com.example.beeplayer.bs.fragment.SearchFragment;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.service.PlayerService;
import com.example.beeplayer.bs.service.TimeCountService;
import com.example.beeplayer.bs.tools.MyTools;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user 主界面
 *
 */
public class MainActivity extends SlidingFragmentActivity
		implements OnClickListener, OnPageChangeListener, android.content.DialogInterface.OnClickListener {
	private ArrayList<Fragment> list_fragment;
	private ViewPager viewPager;
	private TextView tv_mine, tv_find;
	private MineFragment mineFragment;
	private FindFragment findFragment;
	// private RecommendFragment recommendFragment;
	private SlidingMenu slidingMenu;
	private ImageView imag_menu, img_search, img_slide, image_play, imag_list;
	// 滑动块的宽 也是屏幕的1/4
	private int table_wdith;
	// 显示歌曲信息的文本控件
	private TextView tv_song, tv_songer;
	// 用于判断返回键的时间
	private long time = 0;
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private IntentFilter filter;
	private MainBroad receiver;
	// 接受倒计时广播
	private MyBroadCast myBroadCast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		initControl();
		initSlidingMenu();
	}

	/**
	 * 并初始化控件
	 */
	public void initControl() {
		filter = new IntentFilter("controlservice");
		startService(new Intent(this, TimeCountService.class));
		receiver = new MainBroad();
		// 播放控件初始化
		tv_song = (TextView) findViewById(R.id.tv_song);
		tv_songer = (TextView) findViewById(R.id.tv_songer);
		image_play = (ImageView) findViewById(R.id.image_play);
		image_play.setOnClickListener(this);
		imag_list = (ImageView) findViewById(R.id.imag_list);
		imag_list.setOnClickListener(this);

		// 注册退出广播
		myBroadCast = new MyBroadCast();
		registerReceiver(myBroadCast, new IntentFilter("finish"));
	}

	/**
	 * 初始化标题栏控件和 viewpager
	 */
	public void initView() {
		list_fragment = new ArrayList<Fragment>();
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		tv_mine = (TextView) findViewById(R.id.tv_mine);
		tv_mine.setOnClickListener(this);
		tv_find = (TextView) findViewById(R.id.tv_find);
		tv_find.setOnClickListener(this);
		// tv_recommend = (TextView) findViewById(R.id.tv_recommmed);
		// tv_recommend.setOnClickListener(this);
		// 滑动块图片初始化
		img_slide = (ImageView) findViewById(R.id.img_slide);
		table_wdith = MyTools.getScreenWidth(this) / 4;
		img_slide.setMinimumWidth(table_wdith);
		// 初始化菜单图片点击时显示左滑菜单
		imag_menu = (ImageView) findViewById(R.id.img_menu);
		imag_menu.setOnClickListener(this);
		// 初始化搜索点击进入搜索界面
		img_search = (ImageView) findViewById(R.id.img_search);
		img_search.setOnClickListener(this);
		// 添加Fragment添加到ViewPager中
		mineFragment = new MineFragment();
		list_fragment.add(mineFragment);
		findFragment = new FindFragment();
		list_fragment.add(findFragment);
		// recommendFragment = new RecommendFragment();
		// list_fragment.add(recommendFragment);
		fragmentManager = getSupportFragmentManager();
		viewPager.setAdapter(new MyPagerAdapter(fragmentManager, list_fragment));
		viewPager.setOnPageChangeListener(this);
	}

	// 初始化菜单，并设置属性
	public void initSlidingMenu() {
		slidingMenu = getSlidingMenu();
		setBehindContentView(R.layout.layout_leftmenu);
		getSupportFragmentManager().beginTransaction().replace(R.id.fram_leftmenu, new LeftMenu()).commit();
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setBehindWidth((int) (MyTools.getScreenWidth(this) * 0.4));
		slidingMenu.showMenu();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(receiver, filter);
		tv_song.setText(MusicResource.song);
		tv_songer.setText(MusicResource.songer);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}

	/**
	 * 点击不同的TeXView显示不同的Fragment
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 播放控制按钮
			case R.id.image_play:
				Intent intent = new Intent("playerservice");
				intent.putExtra("play_control", MusicResource.play_control);
				sendBroadcast(intent);
				break;
			// 弹窗播放列表打开按钮
			case R.id.imag_list:
				playList();
				break;
			case R.id.tv_mine:
				viewPager.setCurrentItem(0);
				break;
			case R.id.tv_find:
				viewPager.setCurrentItem(1);
				break;
			// case R.id.tv_recommmed:
			// viewPager.setCurrentItem(2);
			// break;
			case R.id.img_menu:
				toggle();
				break;
			case R.id.img_search:
				startFrag(new SearchFragment());
				break;
		}
	}

	/**
	 * 正在播放列表提示框
	 */
	public void playList() {
		Dialog dialog = new Dialog(this, R.style.list_song_dialog);
		dialog.setTitle("正在播放的歌曲……");
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_play_list, null);
		TextView tv_order = (TextView) view.findViewById(R.id.tv_order);
		TextView tv_song = (TextView) view.findViewById(R.id.tv_song);
		TextView tv_songer = (TextView) view.findViewById(R.id.tv_songer);
		ListView listView = (ListView) view.findViewById(R.id.listview_dialog);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < MusicResource.musicResource.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("order", i + 1 + "");
			map.put("song", MusicResource.musicResource.get(i).get("tilte").toString());
			map.put("songer", MusicResource.musicResource.get(i).get("artist").toString());
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.dialog_play_list_adapter,
				new String[] { "order", "song", "songer" }, new int[] { R.id.tv_order, R.id.tv_song, R.id.tv_songer });
		listView.setAdapter(adapter);
		dialog.setContentView(view);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent("playerservice");
				if (MusicResource.music_id == position) {
					intent.putExtra("play_control", MusicResource.play_control);
					sendBroadcast(intent);
				} else {
					MusicResource.music_id = position;
					intent.putExtra("play_control", Constants.MPK_STOP);
					sendBroadcast(intent);
					intent.putExtra("play_control", Constants.MPK_NOREID);
					sendBroadcast(intent);
				}
			}
		});
		Window diWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = diWindow.getAttributes();
		lp.x = 60;
		lp.y = 100;
		diWindow.setAttributes(lp);
		dialog.show();
	}

	/**
	 * 载入播放界面
	 *
	 * @param v
	 */
	public void openPlay(View v) {
		Intent intent = new Intent(this, MusicPlayActivity.class);
		startActivity(intent);
	}

	class MainBroad extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			switch (intent.getIntExtra("re_control", -1)) {
				case Constants.MPK_OTHER:
					tv_song.setText(MusicResource.song);
					tv_songer.setText(MusicResource.songer);
					break;
				case Constants.MPK_OTHER1:

					break;
				default:
					if (MusicResource.play_control == 0x2) {
						image_play.setImageResource(R.drawable.play);
					} else if (MusicResource.play_control == 0x3) {
						image_play.setImageResource(R.drawable.pause);
					}
					break;
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams lp = (LayoutParams) img_slide.getLayoutParams();
		lp.leftMargin = (int) ((arg0 + arg1) * table_wdith);
		img_slide.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	public void startFrag(Fragment fragment) {
		transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.lin_rigth, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	// 对话框的监听
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if (which == -2) {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		if (!fragmentManager.popBackStackImmediate()) {
			if (System.currentTimeMillis() - time < 2000) {
				finish();
			} else {
				time = System.currentTimeMillis();
				Toast.makeText(this, "再次点击退出应用", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class MyBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			MainActivity.this.finish();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopService(new Intent(this, TimeCountService.class));
		stopService(new Intent(this, PlayerService.class));
		unregisterReceiver(myBroadCast);
		super.onDestroy();
	}
}
