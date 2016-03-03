package com.example.beeplayer.bs.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.activity.MenuChild;
import com.example.beeplayer.bs.service.TimeCountService;

import java.util.ArrayList;

public class LeftMenu extends Fragment implements OnClickListener, ServiceConnection {
	ArrayList<Fragment> list;
	TimeCountService.MyBinder myBinder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_slidingmenu, null);
		v.findViewById(R.id.tv_scanMusic).setOnClickListener(this);
		v.findViewById(R.id.tv_sleep_set).setOnClickListener(this);
		v.findViewById(R.id.tv_exit).setOnClickListener(this);
		v.findViewById(R.id.tv_shake).setOnClickListener(this);
		getActivity().bindService(new Intent(getActivity(), TimeCountService.class), this, 0);
		return v;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), MenuChild.class);

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_scanMusic:
			intent.putExtra("ff", 0);
			startActivity(intent);
			break;
		case R.id.tv_sleep_set:
			intent.putExtra("ff", 1);
			startActivity(intent);
			break;
		case R.id.tv_exit:
			getActivity().finish();
			break;
		case R.id.tv_shake:

			myBinder.isShake();
			getActivity().sendBroadcast(new Intent("shake"));
			break;
		}
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		myBinder = (TimeCountService.MyBinder) service;
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
