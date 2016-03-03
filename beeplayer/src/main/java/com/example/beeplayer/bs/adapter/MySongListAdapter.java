package com.example.beeplayer.bs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.beeplayer.bs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MySongListAdapter extends BaseAdapter {
	ArrayList<HashMap<String, String>> list;
	Context context;

	public MySongListAdapter(ArrayList<HashMap<String, String>> list,
			Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vHolder = null;
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.songlist_adapter, null);
			vHolder.tv_searchid = (TextView) convertView
					.findViewById(R.id.tv_searchid);
			vHolder.tv_songname = (TextView) convertView
					.findViewById(R.id.tv_songname);
			vHolder.tv_singername = (TextView) convertView
					.findViewById(R.id.tv_singername);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView tv_searchid, tv_singername, tv_songname;

	}
}
