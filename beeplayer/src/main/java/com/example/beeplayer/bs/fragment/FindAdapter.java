package com.example.beeplayer.bs.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beeplayer.bs.R;

import java.util.List;
import java.util.Map;

public class FindAdapter extends BaseAdapter {

	private List<Map<String, Object>> mList;
	private Context context;

	public FindAdapter(Context context, List<Map<String, Object>> mList) {
		this.context = context;
		this.mList = mList;
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
		notifyDataSetChanged();// //更新适配器
	}

	// 下拉加载 ，使新的数据覆盖旧得数据，然后更新
	public void setData(List<Map<String, Object>> mList) {
		this.mList = mList;
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
			viewHolder.tv_digest = (TextView) convertView.findViewById(R.id.tv_digest);
			viewHolder.tv_replyCount = (TextView) convertView.findViewById(R.id.follow);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			Map<String, Object> map = mList.get(position);
//			ImageTools.setImageUrl(map.get("imgsrc").toString(), viewHolder.image);
//			viewHolder.tv_title.setText(map.get("title").toString());
//			viewHolder.tv_digest.setText(map.get("digest").toString());
//			viewHolder.tv_replyCount.setText(map.get("replyCount").toString());
		}
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView tv_title, tv_digest, tv_replyCount;
	}
}

