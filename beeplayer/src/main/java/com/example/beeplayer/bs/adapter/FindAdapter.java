package com.example.beeplayer.bs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.ImageTools;

import java.util.List;
import java.util.Map;

public class FindAdapter extends BaseAdapter {

	private List<Map<String, Object>> mList;
	private Context context;

	public FindAdapter(Context context, List<Map<String, Object>> mList) {
		this.context = context;
		this.mList = mList;
		MusicResource.musicResourceWeek = this.mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 上拉加载 ，使新加载的数据续在原来消息后面接着显示
	public void addData(List<Map<String, Object>> mList) {
		this.mList.addAll(mList);
		MusicResource.musicResourceWeek = this.mList;
		notifyDataSetChanged();// //更新适配器
	}

	// 下拉加载 ，使新的数据覆盖旧得数据，然后更新
	public void setData(List<Map<String, Object>> mList) {
		this.mList = mList;
		MusicResource.musicResourceWeek = this.mList;
		notifyDataSetChanged();// 更新适配器
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.find_adapter, null);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tv_songer = (TextView) convertView.findViewById(R.id.tv_songer);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map = mList.get(position);
		ImageTools.setImageUrl(map.get("picUrl").toString(), viewHolder.image);
		if (map.get("name") == null) {
			viewHolder.tv_title.setText("");
		} else {
			viewHolder.tv_title.setText(map.get("name").toString());
		}
		if (map.get("singerName") == null) {
			viewHolder.tv_songer.setText("");
		} else {
			viewHolder.tv_songer.setText(map.get("singerName").toString());
		}
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView tv_title, tv_songer, tv_replyCount;
	}
}
