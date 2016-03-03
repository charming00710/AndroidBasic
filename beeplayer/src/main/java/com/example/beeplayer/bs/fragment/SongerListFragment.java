package com.example.beeplayer.bs.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.activity.MainActivity;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.MusicSearch;
import com.example.beeplayer.bs.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongerListFragment extends Fragment implements OnItemClickListener ,PullToRefreshView.OnHeaderRefreshListener {
	private ListView list_mine;
	private List<Map<String, String>> list;
	private SimpleAdapter adapter;
	private PullToRefreshView pull;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		new MusicSearch().getSonger(getActivity());
		View view = inflater.inflate(R.layout.fragment_list_songer, null);
		init(view);
		reList();
		return view;
	}

	public void init(View view){
		list_mine = (ListView) view.findViewById(R.id.list_mine);
		list = new ArrayList<Map<String, String>>();
		adapter = new SimpleAdapter(getActivity(), list, R.layout.list_songer_adapter,
				new String[] { "order", "songer", "sum" }, new int[] { R.id.tv_order, R.id.tv_songer, R.id.tv_sum });
		list_mine.setAdapter(adapter);
		list_mine.setOnItemClickListener(this);
		pull = (PullToRefreshView) view.findViewById(R.id.pull);
		pull.setOnHeaderRefreshListener(this);
		pull.setLoadMoreEnable(false);
	}
	public void reList() {
		list.clear();
		for (int i = 0; i < MusicResource.musicResourceSonger.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("order", i + 1 + "");
			map.put("songer", MusicResource.musicResourceSonger.get(i).get("artist").toString());
			map.put("sum", MusicResource.musicResourceSonger.get(i).get("number").toString() + "首");
			list.add(map);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MusicResource.musicResourceTemp = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < MusicResource.musicResourceLocal.size(); i++) {
			if (MusicResource.musicResourceLocal.get(i).get("artist").equals(list.get(position).get("songer"))) {
				MusicResource.musicResourceTemp.add(MusicResource.musicResourceLocal.get(i));
			}
		}if(MusicResource.musicResourceTemp.size()!=0){
			MusicResource.temp_index = Constants.TEMP_SONGER;
			MainActivity ma = (MainActivity) getActivity();
			ma.startFrag(new SongerOrSpecialFragment());
		}else{
			Toast.makeText(getActivity(), "歌曲已不存在", 0).show();
		}

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		new MusicSearch().getSonger(getActivity());
		reList();
		pull.onHeaderRefreshFinish();
	}
}
