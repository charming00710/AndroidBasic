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

public class HintAdapter extends BaseAdapter {
	ArrayList<HashMap<String, String>> list;
	Context context;

	public HintAdapter(ArrayList<HashMap<String, String>> list,
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
		ViewHolder vHolder=null;
		if(convertView==null){
			vHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.search_adapter, null);
			vHolder.tv_search=(TextView) convertView.findViewById(R.id.tv_search);
			convertView.setTag(vHolder);
		}else {
			vHolder=(ViewHolder) convertView.getTag();
		}
		vHolder.tv_search.setText(list.get(position).get("2").toString());
		return convertView;
	}
	class ViewHolder{
		TextView tv_search;
	}

}
