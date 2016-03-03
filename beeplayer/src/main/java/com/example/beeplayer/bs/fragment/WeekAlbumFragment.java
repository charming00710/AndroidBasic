package com.example.beeplayer.bs.fragment;

import android.app.Dialog;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.json.WeekAlbum;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.ImageTools;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeekAlbumFragment extends Fragment implements OnItemClickListener, OnClickListener {

	private RelativeLayout rela_null;
	private LinearLayout lin_null;
	private ImageView image_alum, image_exit;
	private TextView tv_title, tv_songer;
	private ListView list_song;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> mList;
	private RequestQueue requestQueue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_week_album, null);
		init(view);
		addList("http://api.dongting.com/song/album/" + MusicResource.albumid + "?");
		return view;
	}

	public void init(View view) {
		image_alum = (ImageView) view.findViewById(R.id.image_alum);
		image_exit = (ImageView) view.findViewById(R.id.image_exit);
		image_exit.setOnClickListener(this);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_songer = (TextView) view.findViewById(R.id.tv_songer);
		rela_null = (RelativeLayout) view.findViewById(R.id.rela_null);
		rela_null.setOnClickListener(this);
		lin_null = (LinearLayout) view.findViewById(R.id.lin_null);
		lin_null.setOnClickListener(this);
		mList = new ArrayList<Map<String, Object>>();
		list_song = (ListView) view.findViewById(R.id.list_song);
		adapter = new SimpleAdapter(getActivity(), mList, R.layout.list_latestadapter,
				new String[] { "order", "tilte", "artist" }, new int[] { R.id.tv_order, R.id.tv_song, R.id.tv_songer });
		list_song.setAdapter(adapter);
		list_song.setOnItemClickListener(this);
		requestQueue = Volley.newRequestQueue(getActivity());
	}

	/**
	 * 添加数据到list
	 *
	 * @param url
	 */
	public void addList(String url) {
		if (MusicResource.netWorkState != 0) {
			StringRequest request = new StringRequest(url, new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					WeekAlbum wk = gson.fromJson(arg0, WeekAlbum.class);
					addListSearchSong(wk);
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					dialog("哎！没有搞到数据！");
				}
			});
			requestQueue.add(request);
		} else {
			dialog("忘了联网了吧？");
		}
	}

	/**
	 * 错误提示框
	 *
	 * @param str
	 */
	public void dialog(String str) {
		Dialog dialog = new Dialog(getActivity(), R.style.list_song_dialog);
		dialog.setTitle(str);
		dialog.show();
	}

	/**
	 * 保存搜索得到的资源
	 *
	 */
	public void addListSearchSong(WeekAlbum wk) {
		mList.clear();
		if (wk.getMsg() != null) {
			tv_title.setText(wk.getData().getName());
			tv_songer.setText(wk.getData().getSingerName());
			ImageTools.setImageUrl(wk.getData().getPicUrl(), image_alum);
			for (int i = 0; i < wk.getData().getSongList().size(); i++) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("order", i + 1 + "");
				dataMap.put("songId", wk.getData().getSongList().get(i).getSingerId());
				dataMap.put("tilte", wk.getData().getSongList().get(i).getName());
				dataMap.put("artist", wk.getData().getSongList().get(i).getSingerName());
				dataMap.put("url", wk.getData().getSongList().get(i).getAuditionList().get(0).getUrl());
				mList.add(dataMap);
			}
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent("playerservice");
		if (MusicResource.musicResource != mList) {
			MusicResource.musicResource = mList;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.image_exit:
				getActivity().getSupportFragmentManager().popBackStack();
				break;
			default:
				break;
		}
	}
}
