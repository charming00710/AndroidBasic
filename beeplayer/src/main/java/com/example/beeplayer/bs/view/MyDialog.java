package com.example.beeplayer.bs.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class MyDialog extends AlertDialog implements OnClickListener {
	MyNumberPicker picker;
	long time;
	public MyDialog(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		picker = new MyNumberPicker(context);
		setView(picker);
		setTitle("设置睡眠时间");
		setButton(-2, "取消", (OnClickListener) null);
		setButton3("确定",this );
		/**
		 * 将Number界面的值传给time
		 */
		picker.setOnTimeChanged(new MyNumberPicker.OnTimeChanged() {

			@Override
			public void onTimeChanged(long newVal) {
				// TODO Auto-generated method stub
				time = newVal;
			}
		});

	}

	RightButtonListener rightButtonListener;

	public interface RightButtonListener {
		public void setOnRightButtonChangedListener(long newVal);
	}

	public void setRightButtonListener(RightButtonListener rightButtonListener) {
		this.rightButtonListener = rightButtonListener;
	}
	/**
	 * 点击确定后将time传递给fragment
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		rightButtonListener.setOnRightButtonChangedListener(time);
	}

}
