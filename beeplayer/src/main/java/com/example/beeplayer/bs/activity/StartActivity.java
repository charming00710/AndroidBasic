package com.example.beeplayer.bs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.service.PlayerService;
import com.example.beeplayer.bs.tools.MusicSearch;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author user 开始运行应用，进入开始界面显示图片界面，等待三秒自动跳转到主界面
 *
 */
public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		//启动应用是扫描本地音乐文件
		new MusicSearch().getMusicFile(this);
		//开启音乐播放的服务
		Intent playerService = new Intent(this, PlayerService.class);
		startService(playerService);
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(new Intent(StartActivity.this, MainActivity.class));
				finish();
			}
		};
		timer.schedule(timerTask, 2000);
	}


}