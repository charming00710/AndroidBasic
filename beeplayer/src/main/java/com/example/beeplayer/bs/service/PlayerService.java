package com.example.beeplayer.bs.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.Constants;

/**
 * 音乐播放的服务 0x1没有加载文件0x2播放中0x3暂停中0x4停止0x5上一曲0x6下一曲 根据传递的变量值播放器做出相应的播放回应
 *
 * @author Administrator
 *
 */
public class PlayerService extends Service implements OnPreparedListener, OnCompletionListener {

	ServiceBroad serviceReceiver;
	PlayerBroad receiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// 实例化播放器
	MediaPlayer mp;

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Intent intent = new Intent("controlservice");
			if (msg.arg1 == 1) {
				intent.putExtra("re_control", Constants.MPK_OTHER);
				MusicResource.duration = mp.getDuration();
				MusicResource.induration = mp.getCurrentPosition();
				MusicResource.song = MusicResource.musicResource.get(MusicResource.music_id).get("tilte").toString();
				MusicResource.songer = MusicResource.musicResource.get(MusicResource.music_id).get("artist").toString();
			} else {
				intent.putExtra("re_control", Constants.MPK_OTHER1);
				MusicResource.induration = mp.getCurrentPosition();
			}
			sendBroadcast(intent);
			return false;
		}
	});

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initPlayer();
		getNetwork();
	}

	/**
	 * 初始化播放器mediaplayer
	 */
	public void initPlayer() {
		IntentFilter serviceFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		serviceReceiver = new ServiceBroad();
		registerReceiver(serviceReceiver, serviceFilter);
		IntentFilter filter = new IntentFilter("playerservice");
		receiver = new PlayerBroad();
		registerReceiver(receiver, filter);
		mp = new MediaPlayer();
		mp.setOnPreparedListener(this);
		mp.setOnCompletionListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopPlay();
		unregisterReceiver(serviceReceiver);
		unregisterReceiver(receiver);
	}

	/**
	 * 播放器资源准备
	 *
	 * param context
	 */
	public void preparePlay() {
		if (MusicResource.musicResource.get(MusicResource.music_id) != null) {
			try {
				if (MusicResource.musicResource.get(MusicResource.music_id).get("path") != null) {
					String path = MusicResource.musicResource.get(MusicResource.music_id).get("path").toString();
					if (path.equals("")) {
						paseNull();
					} else {
						mp.reset();
						mp.setDataSource(path);
						mp.prepareAsync();
						// 添加到最近播放列表
						MusicResource.musicResourceLatest.add(MusicResource.musicResource.get(MusicResource.music_id));
					}
				} else {
					String url = MusicResource.musicResource.get(MusicResource.music_id).get("url").toString();
					if (url.equals("")) {
						paseNull();
					} else {
						Uri uri = Uri.parse(url);
						mp.reset();
						mp.setDataSource(this, uri);
						mp.prepareAsync();
						MusicResource.musicResourceLatest.add(MusicResource.musicResource.get(MusicResource.music_id));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			paseNull();
		}
	}

	/**
	 * 跳过空连接
	 */
	public void paseNull() {
		if (MusicResource.music_id < MusicResource.musicResource.size() - 1) {
			nextMusic();
		}
	}

	/**
	 * 控制播放 播放和暂停 停止 上一曲 下一曲
	 *
	 */
	public void playerControl(int play_control) {
		int re_control = play_control;
		Intent intent = new Intent("controlservice");
		switch (play_control) {
			case Constants.MPK_NOREID:
				preparePlay();
				re_control = Constants.MPK_PLAYING;
				break;
			case Constants.MPK_PLAYING:
				mp.pause();
				re_control = Constants.MPK_PAUSE;
				break;
			case Constants.MPK_PAUSE:
				startPlay();
				re_control = Constants.MPK_PLAYING;
				break;
			case Constants.MPK_STOP:
				stopPlay();
				re_control = Constants.MPK_NOREID;
				break;
			case Constants.MPK_LAST:
				MusicResource.music_id--;
				if (MusicResource.music_id < 0) {
					MusicResource.music_id = MusicResource.musicResource.size() - 1;
				}
				preparePlay();
				re_control = Constants.MPK_PLAYING;
				break;
			case Constants.MPK_NEXT:
				nextMusic();
				re_control = Constants.MPK_PLAYING;
				break;
		}
		MusicResource.play_control = re_control;
		intent.putExtra("re_control", re_control);
		sendBroadcast(intent);
	}

	/**
	 * 调到下一曲
	 */
	public void nextMusic() {
		MusicResource.music_id++;
		if (MusicResource.music_id > MusicResource.musicResource.size() - 1) {
			MusicResource.music_id = 0;
		}
		preparePlay();
	}

	public void stopPlay() {
		if (mp.isPlaying()) {
			mp.stop();
		}
	}

	/**
	 * 准备状态监听 准备完成后启动播放
	 */
	@Override
	public void onPrepared(final MediaPlayer mp) {
		startPlay();
	}

	/**
	 * 启动播放
	 */
	public void startPlay() {
		mp.start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.arg1 = 1;
				handler.sendMessage(msg);
				while (mp.isPlaying()) {
					Message msg1 = new Message();
					msg1.arg1 = 0;
					handler.sendMessage(msg1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 设置自动播放
	 *
	 * @param mp
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.reset();
		switch (MusicResource.looping) {

			case 0:// 列表循环
				nextMusic();
				break;

			case 1:// 顺序播放
				if (MusicResource.music_id < MusicResource.musicResource.size() - 1) {
					MusicResource.music_id++;
					preparePlay();
				} else {
					MusicResource.music_id = 0;
					Intent intent = new Intent("controlservice");
					intent.putExtra("re_control", Constants.MPK_OTHER);
					MusicResource.duration = Constants.SONG_DURATION;
					MusicResource.induration = Constants.SONG_DURATION;
					MusicResource.song = Constants.SONG;
					MusicResource.songer = Constants.SONGER;
					sendBroadcast(intent);
				}
				break;
			case 2:
				MusicResource.music_id = (int) (Math.random() * (MusicResource.musicResource.size() - 1));
				preparePlay();
				break;
			case 3:// 单曲循环
				preparePlay();
				break;

			default:
				break;
		}
	}

	/**
	 * 获取网络状态
	 */
	public void getNetwork() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		// 得到当前网络
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		// NetworkInfo对象用来描述网络信息。
		if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
			MusicResource.netWorkState = 0;
		} else {// 可能联网
			int networkType = activeNetworkInfo.getType();
			if (networkType == ConnectivityManager.TYPE_WIFI) {
				MusicResource.netWorkState = 1;// 当前成功连接WIFI
			} else if (networkType == ConnectivityManager.TYPE_MOBILE) {
				MusicResource.netWorkState = 2;// 当前成功连接3G网络
			}
		}
	}

	class PlayerBroad extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (MusicResource.musicResource.size() != 0) {
				if (intent.getIntExtra("play_control", -1) == Constants.MPK_OTHER) {
					mp.seekTo(intent.getIntExtra("other", -1));
				} else {
					playerControl(intent.getIntExtra("play_control", -1));
				}
			} else {
				Toast.makeText(context, "请选择播放列表", 0).show();
			}
		}
	}

	class ServiceBroad extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			getNetwork();
		}
	}
}
