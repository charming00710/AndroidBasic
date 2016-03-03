package com.example.beeplayer.bs.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.MusicSearch;
import com.example.beeplayer.bs.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地播放的播放列表
 *
 * @author Administrator
 *
 */
public class SongListFragment extends Fragment
		implements OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener, OnItemLongClickListener {
	private ListView list_mine;
	private List<Map<String, String>> list;
	private SimpleAdapter adapter;
	private PullToRefreshView pull;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_song, null);
		init(view);
		reList();
		return view;
	}

	public void init(View view) {
		list_mine = (ListView) view.findViewById(R.id.list_mine);
		list = new ArrayList<Map<String, String>>();
		adapter = new SimpleAdapter(getActivity(), list, R.layout.list_song_adapter,
				new String[] { "order", "song", "songer" }, new int[] { R.id.tv_order, R.id.tv_song, R.id.tv_songer });
		list_mine.setAdapter(adapter);
		list_mine.setOnItemClickListener(this);
		list_mine.setOnItemLongClickListener(this);
		pull = (PullToRefreshView) view.findViewById(R.id.pull);
		pull.setOnHeaderRefreshListener(this);
		pull.setLoadMoreEnable(false);
	}

	public void reList() {
		list.clear();
		for (int i = 0; i < MusicResource.musicResourceLocal.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("order", i + 1 + "");
			map.put("song", MusicResource.musicResourceLocal.get(i).get("tilte").toString());
			map.put("songer", MusicResource.musicResourceLocal.get(i).get("artist").toString());
			list.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent("playerservice");
		if (MusicResource.musicResource != MusicResource.musicResourceLocal) {
			MusicResource.musicResource = MusicResource.musicResourceLocal;
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

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		MusicResource.musicResourceLocal.clear();
		new MusicSearch().getMusicFile(getActivity());
		reList();
		pull.onHeaderRefreshFinish();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		list.remove(position);
		adapter.notifyDataSetChanged();
		new MusicSearch().deletsong(getActivity(), position);
		return true;
	}
}
