package com.example.beeplayer.bs.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.example.beeplayer.bs.tools.Constants;

public class TimeCountService extends Service implements SensorEventListener {
	SensorManager sensorManager;
	BroadcastReceiver receiver;
	Sensor sensor;
	boolean condition = false;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		receiver = new MyBroadCast();
		registerReceiver(receiver, new IntentFilter("shake"));
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	public class MyBinder extends Binder {
		private TimeCount timer;

		/**
		 * 判断选中状态
		 */
		private long which;
		/**
		 * 当选择自定义后判断是否再次弹出Dialog
		 */
		private boolean customer = false;

		/**
		 *
		 *            控制是否开始摇一摇
		 */
		public void isShake() {
			TimeCountService.this.condition = !TimeCountService.this.condition;
		}

		public void setWhich(long which) {
			this.which = which;
		}

		public boolean getCustomer() {
			return customer;
		}

		public void setCustomer(boolean customer) {
			this.customer = customer;
		}

		public long getWhich() {
			return which;
		}

		public void newTimeCount(long millisInFuture, long countDownInterval) {
			this.which = millisInFuture;
			timer = new TimeCount(millisInFuture, countDownInterval);

		}

		public TimeCount getTimer() {
			return timer;
		}

		public void setBinderTimeChangeListener(TimeChangeListener listener) {
			setTimeChangeListener(listener);

		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	TimeChangeListener listener;

	/**
	 * 将计时时间传递到Fragment
	 *
	 * @author user
	 *
	 */
	public interface TimeChangeListener {
		void getMillisUntilFinished(long millisUntilFinished);
	}

	public void setTimeChangeListener(TimeChangeListener listener) {
		this.listener = listener;
	}

	public class TimeCount extends CountDownTimer {
		/**
		 * @param millisInFuture
		 *            这个参数是总的时间
		 * @param countDownInterval
		 *            这个是减少的时间间隔
		 */
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub

		}

		/**
		 * 计时过程中触发
		 */
		@Override
		public void onTick(long millisUntilFinished) {
			listener.getMillisUntilFinished(millisUntilFinished);
		}

		/**
		 * 计时完成后发送广播关闭Activity
		 */
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

			sendBroadcast(new Intent("finish"));

		}

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		if (Math.abs(event.values[0]) > 15||Math.abs(event.values[1])>15) {
			Log.i("ff", "发送广播"+Math.abs(event.values[0]));
			Intent intent = new Intent("playerservice");
			intent.putExtra("play_control", Constants.MPK_NEXT);
			sendBroadcast(intent);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	class MyBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if (condition) {

				sensorManager.registerListener(TimeCountService.this, sensor,
						SensorManager.SENSOR_DELAY_NORMAL);
			} else {

				sensorManager.unregisterListener(TimeCountService.this, sensor);
			}
		}

	}

}
