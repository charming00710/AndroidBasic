package com.example.beeplayer.bs.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.activity.MainActivity;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineFragment extends Fragment implements OnClickListener, OnItemClickListener {
	private TextView tv_play_list, tv_latest;
	private ImageView image_bauck;
	private ListView latest;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mine, null);
		tv_play_list = (TextView) view.findViewById(R.id.tv_play_list);
		tv_latest = (TextView) view.findViewById(R.id.tv_latest);
		tv_latest.setOnClickListener(this);
		image_bauck = (ImageView) view.findViewById(R.id.image_back);
		image_bauck.setImageResource(R.drawable.mine_bauck);
		tv_play_list.setOnClickListener(this);
		latest = (ListView) view.findViewById(R.id.latest);
		latest.setOnItemClickListener(this);
		list = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(getActivity(), list, R.layout.list_latestadapter,
				new String[] { "order", "song", "songer" }, new int[] { R.id.tv_order, R.id.tv_song, R.id.tv_songer });
		latest.setAdapter(adapter);
		return view;
	}

	public void reListView() {
		list.clear();
		for (int i = 0; i < MusicResource.musicResourceLatest.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order", i + 1 + "");
			map.put("song", MusicResource.musicResourceLatest.get(i).get("tilte"));
			map.put("songer", MusicResource.musicResourceLatest.get(i).get("artist"));
			list.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_play_list:
			((MainActivity) getActivity()).startFrag(new FragmentLocalMusic());
			break;
		case R.id.tv_latest:
			reListView();
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("playerservice");
		if (MusicResource.musicResource != MusicResource.musicResourceLatest) {
			MusicResource.musicResource = MusicResource.musicResourceLatest;
			MusicResource.music_id = position;
			intent.putExtra("play_control", Constants.MPK_STOP);
			getActivity().sendBroadcast(intent);
			intent.putExtra("play_control", Constants.MPK_NOREID);
			getActivity().sendBroadcast(intent);
		} else if (MusicResource.music_id == position) {
			intent.putExtra("play_control", MusicResource.play_control);
			getActivity().sendBroadcast(intent);
		} else {
			MusicResource.music_id = position;
			intent.putExtra("play_control", Constants.MPK_STOP);
			getActivity().sendBroadcast(intent);
			intent.putExtra("play_control", Constants.MPK_NOREID);
			getActivity().sendBroadcast(intent);
		}
	}

}
