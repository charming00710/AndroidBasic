package com.example.beeplayer.bs.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.activity.MainActivity;
import com.example.beeplayer.bs.json.FindWeek;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.view.PullToRefreshView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindFragment extends Fragment implements OnItemClickListener, PullToRefreshView.OnFooterLoadListener {
	private GridView find_grid;
	private List<Map<String, Object>> mList;
	private FindAdapter adapter;
	private PullToRefreshView pull;
	private ProgressDialog pd;
	private RequestQueue requestQueue;
	private String newAlbum = "http://api.dongting.com/misc/album/new?page=";
	private int page = 1;
	private int type = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_find, null);
		inifind(view);
		refrehView();
		return view;
	}

	/**
	 * 初始化
	 *
	 * @param view
	 */
	public void inifind(View view) {
		find_grid = (GridView) view.findViewById(R.id.list_find);
		find_grid.setOnItemClickListener(this);
		mList = new ArrayList<Map<String, Object>>();
		pull = (PullToRefreshView) view.findViewById(R.id.pull);
		pull.setOnFooterLoadListener(this);
		pull.setPullRefreshEnable(false);
		adapter = new FindAdapter(getActivity(), mList);
		find_grid.setAdapter(adapter);
		requestQueue = Volley.newRequestQueue(getActivity());
	}

	public void refrehView() {
		if (type == 0 && MusicResource.netWorkState != 0) {
			pd = new ProgressDialog(getActivity());
			pd.setMessage("正在加载……");
			pd.show();
		}
		addList(newAlbum + page + "&size=10");
	}

	public void setAdapters() {
		if (type == 0) {
			adapter.setData(mList);// 第一次进入，加载第一次的数据，覆盖原来的空列表，此方法在适配器中
		} else {
			adapter.addData(mList);// 加载数据，续借在原来数据后面，此方法在适配器中
			pull.onFooterLoadFinish();
		}
		if (type == 0) {
			pd.dismiss();// 首次进入就显示一次
		}
	}

	public void addList(String url) {
		if (MusicResource.netWorkState != 0) {

			StringRequest request = new StringRequest(url, new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					FindWeek findWeek = gson.fromJson(arg0, FindWeek.class);
					getData(findWeek);
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					dialog("哎！没有搞到数据！");
					finishadd();
				}
			});
			requestQueue.add(request);
		} else {
			dialog("忘了联网了吧？");
			finishadd();
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
	 * 关闭请求错误时的上拉下拉框
	 */
	public void finishadd() {
		if (type == 2) {
			pull.onFooterLoadFinish();
		}
	}

	private void getData(FindWeek findWeek) {
		if (findWeek.getData() != null) {
			mList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < findWeek.getData().size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("albumId", findWeek.getData().get(i).getAlbumId());
				map.put("name", findWeek.getData().get(i).getName());
				map.put("singerName", findWeek.getData().get(i).getSingerName());
				map.put("picUrl", findWeek.getData().get(i).getPicUrl());
				map.put("publishYear", findWeek.getData().get(i).getPublishYear());
				map.put("publishDate", findWeek.getData().get(i).getPublishDate());
				map.put("songs", findWeek.getData().get(i).getSongs());
				mList.add(map);
			}
			setAdapters();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MusicResource.albumid = MusicResource.musicResourceWeek.get(position).get("albumId").toString();
		MainActivity ma = (MainActivity)getActivity();
		ma.startFrag(new WeekAlbumFragment());
	}

	@Override
	public void onFooterLoad(PullToRefreshView view) {
		// TODO Auto-generated method stub
		page += 1;
		type = 2;
		refrehView();
	}
}
