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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongerOrSpecialFragment extends Fragment implements OnItemClickListener, OnClickListener {
	private ListView list_songer_special;
	private LinearLayout lin_null;
	private TextView tv_title;
	private ImageView image_exit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_songer_special, null);
		init(view);
		return view;
	}

	private void init(View view) {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		switch (MusicResource.temp_index) {
			case Constants.TEMP_SONGER:
				tv_title.setText(MusicResource.musicResourceTemp.get(0).get("artist").toString());
				break;
			case Constants.TEMP_SPECIAL:
				tv_title.setText(MusicResource.musicResourceTemp.get(0).get("album").toString());
				break;
			case Constants.TEMP_NETWORK:
				tv_title.setText("网络歌曲");
				break;

			default:
				break;
		}
		image_exit = (ImageView) view.findViewById(R.id.image_exit);
		image_exit.setOnClickListener(this);
		list_songer_special = (ListView) view.findViewById(R.id.list_songer_special);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < MusicResource.musicResourceTemp.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("order", i + 1 + "");
			map.put("song", MusicResource.musicResourceTemp.get(i).get("tilte").toString());
			map.put("songer", MusicResource.musicResourceTemp.get(i).get("artist").toString());
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.list_songer_special_adapter,
				new String[] { "order", "song", "songer" }, new int[] { R.id.tv_order, R.id.tv_song, R.id.tv_songer });
		list_songer_special.setAdapter(adapter);
		list_songer_special.setOnItemClickListener(this);
		lin_null = (LinearLayout) view.findViewById(R.id.lin_null);
		lin_null.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("playerservice");
		if (MusicResource.musicResource != MusicResource.musicResourceTemp) {
			MusicResource.musicResource = MusicResource.musicResourceTemp;
			MusicResource.music_id = position;
			intent.putExtra("play_control", Constants.MPK_STOP);
			getActivity().sendBroadcast(intent);
			intent.putExtra("play_control", Constants.MPK_NOREID);
			getActivity().sendBroadcast(intent);
		}else if (MusicResource.music_id == position) {
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.image_exit) {
			getActivity().getSupportFragmentManager().popBackStack();
		}
	}
}
