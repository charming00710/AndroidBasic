package com.example.beeplayer.bs.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.fragment.ScanMusic;
import com.example.beeplayer.bs.fragment.SetSleep;

public class MenuChild extends FragmentActivity {
	MyBroadCast myBroadCast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuchild);
		int a = getIntent().getIntExtra("ff", 3);
		if (a == 0) {
			changeFrag(new ScanMusic());
		} else if (a == 1) {
			changeFrag(new SetSleep());
		}
		myBroadCast = new MyBroadCast();
		;
		registerReceiver(myBroadCast, new IntentFilter("finish"));
	}

	private void changeFrag(Fragment fm) {
		// TODO Auto-generated method stu
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frag_menuchild, fm).commit();

	}

	class MyBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			MenuChild.this.finish();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(myBroadCast);
		super.onDestroy();
	}
}
