package com.example.beeplayer.bs.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.json.SearchImage;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.ImageTools;
import com.example.beeplayer.bs.tools.MyTools;
import com.google.gson.Gson;

/**
 * 音乐播放大图界面
 *
 * @author Administrator
 *
 */
public class MusicPlayActivity extends Activity implements OnSeekBarChangeListener {
	private IntentFilter filter;
	private MusicPlaybroad receiver;
	// 进度条
	private SeekBar seekBar;
	private TextView tv_duration, tv_induration, tv_song, tv_songer;
	// 播放控制按钮
	private ImageView image_play;
	private ImageView image_last, image_next, image_bauck, image_playmode;
	private RequestQueue requestQueue;
	private String songer = "";
	private String[] arr_url;
	private int index = 0;
	private int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_play);
		initcontrol();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(receiver, filter);
		iniOnResume();
		getImageUrl(MusicResource.songer);
	}

	public void iniOnResume() {
		seekBar.setMax(MusicResource.duration);
		seekBar.setProgress(MusicResource.induration);
		tv_song.setText(MusicResource.song);
		tv_songer.setText(MusicResource.songer);
		tv_duration.setText(MyTools.getMinute(MusicResource.duration));
		tv_induration.setText(MyTools.getMinute(MusicResource.induration));
		requestQueue = Volley.newRequestQueue(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}

	public void initcontrol() {
		filter = new IntentFilter("controlservice");
		receiver = new MusicPlaybroad();
		image_play = (ImageView) findViewById(R.id.image_play);
		image_bauck = (ImageView) findViewById(R.id.image_bauck);
		image_playmode = (ImageView) findViewById(R.id.image_playmode);
		looping();
		tv_duration = (TextView) findViewById(R.id.tv_duration);
		tv_induration = (TextView) findViewById(R.id.tv_induration);
		tv_song = (TextView) findViewById(R.id.tv_song);
		tv_songer = (TextView) findViewById(R.id.tv_songer);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setMax(MusicResource.duration);
		seekBar.setProgress(MusicResource.induration);
		seekBar.setOnSeekBarChangeListener(this);
	}

	/**
	 * 控制按钮
	 *
	 * @param v
	 */
	public void play(View v) {
		Intent intent = new Intent("playerservice");
		switch (v.getId()) {
			case R.id.image_play:
				intent.putExtra("play_control", MusicResource.play_control);
				break;
			case R.id.image_last:
				intent.putExtra("play_control", Constants.MPK_LAST);
				break;
			case R.id.image_next:
				intent.putExtra("play_control", Constants.MPK_NEXT);
				break;
			default:
				break;
		}
		sendBroadcast(intent);
	}

	public void button(View v) {
		switch (v.getId()) {
			case R.id.image_exit:
				finish();
				break;
			case R.id.searchimage:
				customImage();
				break;
			case R.id.image_playmode:
				MusicResource.looping++;
				if (MusicResource.looping > Constants.MPM_SINGLE_LOOP_PLAY) {
					MusicResource.looping = Constants.MPM_LIST_LOOP_PLAY;
				}
				looping();
				break;
			default:
				break;
		}
	}

	public void looping() {
		switch (MusicResource.looping) {
			case Constants.MPM_LIST_LOOP_PLAY:
				image_playmode.setImageResource(R.drawable.img_playmode_repeat_playinglist);
				Toast.makeText(this, "已设置列表循环", Toast.LENGTH_SHORT).show();
				break;
			case Constants.MPM_ORDER_PLAY:
				image_playmode.setImageResource(R.drawable.img_playmode_sequence_playinglist);
				Toast.makeText(this, "已设置顺序播放", Toast.LENGTH_SHORT).show();
				break;
			case Constants.MPM_RANDOM_PLAY:
				image_playmode.setImageResource(R.drawable.img_playmode_shuffle_playinglist);
				Toast.makeText(this, "已设置随机播放", Toast.LENGTH_SHORT).show();
				break;
			case Constants.MPM_SINGLE_LOOP_PLAY:
				image_playmode.setImageResource(R.drawable.img_playmode_repeatone_playinglist);
				Toast.makeText(this, "已设置单曲循环", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
	}

	/**
	 * 自定义搜索图片
	 */
	public void customImage() {
		Dialog dialog = new Dialog(this, R.style.image_songer_dialog);
		View view = LayoutInflater.from(this).inflate(R.layout.custom_image, null);
		final EditText ed = (EditText) view.findViewById(R.id.editText1);
		Button search = (Button) view.findViewById(R.id.button2);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getImageUrl(ed.getText().toString());
			}
		});
		dialog.setContentView(view);
		Window diWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = diWindow.getAttributes();
		lp.x = 100;
		lp.y = 150;
		diWindow.setAttributes(lp);
		dialog.show();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if (fromUser) {
			Intent intent = new Intent("playerservice");
			intent.putExtra("play_control", Constants.MPK_OTHER);
			intent.putExtra("other", progress);
			sendBroadcast(intent);
		}
		tv_induration.setText(MyTools.getMinute(progress));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("playerservice");
		intent.putExtra("play_control", Constants.MPK_PLAYING);
		sendBroadcast(intent);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("playerservice");
		intent.putExtra("play_control", Constants.MPK_PAUSE);
		sendBroadcast(intent);
	}

	public void getImageUrl(String songer) {
		arr_url = null;
		if (MusicResource.netWorkState != 0) {

			StringRequest request = new StringRequest(
					"http://search.dongting.com/artwork/search?artist=" + MyTools.toUnicode(songer),
					new Listener<String>() {

						@Override
						public void onResponse(String arg0) {
							// TODO Auto-generated method stub
							Gson gson = new Gson();
							SearchImage si = gson.fromJson(arg0, SearchImage.class);
							getImageList(si);
						}
					}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					dialog("哎！没有搞到图片！");
				}
			});
			requestQueue.add(request);
		} else {
			dialog("忘了联网了吧？");
		}
	}

	/**
	 * 错误提示框
	 *
	 * @param str
	 */
	public void dialog(String str) {
		Dialog dialog = new Dialog(this, R.style.list_song_dialog);
		dialog.setTitle(str);
		dialog.show();
	}

	public void getImageList(SearchImage si) {
		if (si.getMsg() != null) {
			arr_url = new String[si.getData().get(0).getPicUrls().size()];
			for (int i = 0; i < si.getData().get(0).getPicUrls().size(); i++) {
				arr_url[i] = si.getData().get(0).getPicUrls().get(i).getPicUrl();
			}
			if (arr_url.length != 0) {
				ImageTools.setImageUrlH(arr_url[0], image_bauck);
			} else {
				dialog("没有图片，手动搜索试试");
				image_bauck.setImageResource(R.drawable.xiaomifeng);
			}
			songer = si.getData().get(0).getName();
		}
	}

	class MusicPlaybroad extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			switch (intent.getIntExtra("re_control", -1)) {
				case Constants.MPK_OTHER:
					seekBar.setMax(MusicResource.duration);
					seekBar.setProgress(MusicResource.induration);
					tv_song.setText(MusicResource.song);
					tv_songer.setText(MusicResource.songer);
					tv_duration.setText(MyTools.getMinute(MusicResource.duration));
					if (!tv_songer.getText().equals(songer)) {
						getImageUrl(MusicResource.songer);
					}
					break;
				case Constants.MPK_OTHER1:
					seekBar.setProgress(MusicResource.induration);
					// 间隔一定时间更换图片
					number++;
					if (number >= 20) {
						number = 0;
						if (arr_url != null && arr_url.length != 0) {
							index++;
							if (index > arr_url.length - 1) {
								index = 0;
							}
							ImageTools.setImageUrlH(arr_url[index], image_bauck);
						}
					}
					break;
				default:
					if (MusicResource.play_control == 0x2) {
						image_play.setImageResource(R.drawable.play1);
					} else if (MusicResource.play_control == 0x3) {
						image_play.setImageResource(R.drawable.play2);
					}
					break;
			}
		}
	}
}
