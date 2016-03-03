package com.example.beeplayer.bs.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;

import com.example.beeplayer.bs.R;


public class MyNumberPicker extends FrameLayout implements OnScrollListener,
		OnValueChangeListener {
	public NumberPicker hour, minute, text;
	long timeCount = 0;
	long timeMinute = 0;
	long timeHour = 0;

	public MyNumberPicker(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflate(context, R.layout.view_numberpicker, this);
		hour = (NumberPicker) findViewById(R.id.np_hour);
		hour.setMaxValue(23);
		hour.setMinValue(0);

		hour.setOnScrollListener(this);
		hour.setOnValueChangedListener(this);

		minute = (NumberPicker) findViewById(R.id.np_minute);
		minute.setMaxValue(59);
		minute.setMinValue(0);
		minute.setOnScrollListener(this);
		minute.setOnValueChangedListener(this);
		text = (NumberPicker) findViewById(R.id.np_text);
		text.setDisplayedValues(new String[] { "小时" });

	}

	@Override
	public void onScrollStateChange(NumberPicker view, int scrollState) {
		// TODO Auto-generated method stub
		switch (scrollState) {
			case SCROLL_STATE_IDLE:

				break;
			case SCROLL_STATE_FLING:
				break;
			case SCROLL_STATE_TOUCH_SCROLL:
				break;

			default:
				break;
		}
	}

	/**
	 * 当值改变将其转换成毫秒传递值Dialog界面
	 */
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub


		if (picker == hour) {
			timeHour = newVal * 60 * 1000 * 60;

		}
		if (picker == minute) {
			timeMinute = newVal * 60 * 1000;

		}

		timeCount = timeHour + timeMinute;

		onTimeChanged.onTimeChanged(timeCount);
	}

	public void setOnTimeChanged(OnTimeChanged onTimeChanged) {
		this.onTimeChanged = onTimeChanged;
	}

	OnTimeChanged onTimeChanged;

	public interface OnTimeChanged {
		public void onTimeChanged(long newVal);
	}

}
