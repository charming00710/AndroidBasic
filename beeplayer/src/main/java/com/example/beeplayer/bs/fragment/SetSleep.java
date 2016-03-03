package com.example.beeplayer.bs.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.service.TimeCountService;
import com.example.beeplayer.bs.view.MyDialog;

public class SetSleep extends Fragment implements OnCheckedChangeListener, ServiceConnection, MyDialog.RightButtonListener {
	private RadioGroup radioGroup;
	private RadioButton setsleep_off, setsleep_ten, setsleep_twenty, setsleep_thirty, setsleep_sixty, setsleep_ninety,
			setsleep_custom;
	private TextView tv_showTime;
	private TimeCountService.MyBinder binder;
	/**
	 * 根据是否为0判断是否开启计时或取消计时
	 */
	private long time;
	/**
	 * 判断选中状态是用户点击还是自己设置的
	 */
	private boolean fromUser = true;
	/**
	 * 常量默认选项十分钟、二十分钟、三十分钟、九十分钟
	 */
	private static final int TEN = 10 * 60 * 1000;
	private static final int TWENTY = 20 * 60 * 1000;
	private static final int THIRTY = 30 * 60 * 1000;
	private static final int SIXTY = 60 * 60 * 1000;
	private static final int NINETY = 90 * 60 * 1000;
	/**
	 * 倒计时的间隔为一秒
	 */
	private static final int COUNTDOWNVALU = 1000;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_setsleep, null);
		init(v);

		return v;
	}

	private void init(View v) {

		getActivity().bindService(new Intent(getActivity(), TimeCountService.class), this, 0);
		radioGroup = (RadioGroup) v.findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		setsleep_off = (RadioButton) v.findViewById(R.id.setsleep_off);

		setsleep_ten = (RadioButton) v.findViewById(R.id.setsleep_ten);
		setsleep_twenty = (RadioButton) v.findViewById(R.id.setsleep_twenty);
		setsleep_thirty = (RadioButton) v.findViewById(R.id.setsleep_thirty);
		setsleep_sixty = (RadioButton) v.findViewById(R.id.setsleep_sixty);
		setsleep_ninety = (RadioButton) v.findViewById(R.id.setsleep_ninety);
		setsleep_custom = (RadioButton) v.findViewById(R.id.setsleep_custom);
		tv_showTime = (TextView) v.findViewById(R.id.tv_showtime);
		tv_showTime.setTextColor(Color.RED);
		/**
		 * 默认选择不开启状态
		 */

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
			case R.id.setsleep_off:
				if (binder != null && binder.getTimer() != null) {
					binder.getTimer().cancel();
					tv_showTime.setText("计时结束后，将暂停并退出播放器");
					time = 0;
				}
				fromUser = true;
				binder.setCustomer(false);
				binder.newTimeCount(0, COUNTDOWNVALU);
				break;
			case R.id.setsleep_ten:
				if (fromUser) {
					if (time != 0) {
						binder.getTimer().cancel();
					}
					binder.newTimeCount(TEN, COUNTDOWNVALU);
					binder.getTimer().start();
				}
				fromUser = true;
				binder.setCustomer(false);
				break;
			case R.id.setsleep_twenty:
				if (fromUser) {
					if (time != 0) {
						binder.getTimer().cancel();
					}
					binder.newTimeCount(TWENTY, COUNTDOWNVALU);
					binder.getTimer().start();
				}
				fromUser = true;
				binder.setCustomer(false);
				break;
			case R.id.setsleep_thirty:
				if (fromUser) {
					if (time != 0) {
						binder.getTimer().cancel();
					}
					binder.newTimeCount(THIRTY, COUNTDOWNVALU);
					binder.getTimer().start();
				}
				fromUser = true;
				binder.setCustomer(false);
				break;
			case R.id.setsleep_sixty:
				if (fromUser) {
					if (time != 0) {
						binder.getTimer().cancel();
					}
					binder.newTimeCount(SIXTY, COUNTDOWNVALU);
					binder.getTimer().start();
				}
				fromUser = true;
				binder.setCustomer(false);
				break;
			case R.id.setsleep_ninety:
				if (fromUser) {
					if (time != 0) {
						binder.getTimer().cancel();
					}
					binder.newTimeCount(NINETY, COUNTDOWNVALU);
					binder.getTimer().start();
				}
				fromUser = true;
				binder.setCustomer(false);
				break;
			case R.id.setsleep_custom:
				/**
				 * 点击自定义后是否显示Dialog
				 */
				if (!binder.getCustomer()) {

					MyDialog dialog = new MyDialog(getActivity());

					dialog.setRightButtonListener(this);
					dialog.show();
				}
				fromUser = true;
				break;

		}

	}

	/**
	 * 点击确定后将值传递给Service开始计时
	 */
	@Override
	public void setOnRightButtonChangedListener(long newVal) {
		// TODO Auto-generated method stub

		if (fromUser && newVal != 0) {
			if (time != 0) {
				binder.getTimer().cancel();
			}
			binder.newTimeCount(newVal, COUNTDOWNVALU);
			binder.getTimer().start();
		}

		// 点击确定后设置服务中的布尔值为true
		binder.setCustomer(true);

	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		binder = (TimeCountService.MyBinder) service;
		/**
		 * 如果计时中退出再次进入页面根据服务的时间设置按钮选中状态
		 */
		if (binder.getWhich() == 0) {

			setsleep_off.setChecked(true);
		} else if (binder.getWhich() == TEN) {
			fromUser = false;
			setsleep_ten.setChecked(true);
		} else if (binder.getWhich() == TWENTY) {
			fromUser = false;
			setsleep_twenty.setChecked(true);
		} else if (binder.getWhich() == THIRTY) {
			fromUser = false;
			setsleep_thirty.setChecked(true);

		} else if (binder.getWhich() == SIXTY) {
			fromUser = false;
			setsleep_sixty.setChecked(true);
		} else if (binder.getWhich() == NINETY) {
			fromUser = false;
			setsleep_ninety.setChecked(true);
		} else if (binder.getCustomer()) {
			fromUser = false;
			setsleep_custom.setChecked(true);
		}
		/**
		 * 接口回调服务中的时间
		 */
		binder.setBinderTimeChangeListener(new TimeCountService.TimeChangeListener() {

			@Override
			public void getMillisUntilFinished(long millisUntilFinished) {

				time = millisUntilFinished;
				tv_showTime.setText(millisUntilFinished / 1000 / 60 + "分" + millisUntilFinished % (1000 * 60) / 1000
						+ "秒过后，将暂停并退出播放器");


			}
		});
	}


	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unbindService(this);
		super.onDestroy();
	}

}
