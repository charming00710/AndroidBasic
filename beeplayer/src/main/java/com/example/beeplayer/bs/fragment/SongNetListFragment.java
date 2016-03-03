package com.example.beeplayer.bs.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.adapter.SongNetListAdapter;
import com.example.beeplayer.bs.json.SearchSong;
import com.example.beeplayer.bs.resource.Constants;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.MyTools;
import com.example.beeplayer.bs.view.PullToRefreshView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络歌曲展示歌曲页面
 *
 * @author Administrator
 *
 */
public class SongNetListFragment extends Fragment
		implements OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterLoadListener {
	private TextView tv_totalCount;
	private ListView net_song_list;
	private List<Map<String, Object>> mList;
	private SongNetListAdapter adapter;
	private RequestQueue requestQueue;
	private PullToRefreshView pull;
	private ProgressDialog pd;// 加载圈
	// 歌曲搜索请求
	private String get_songa = "http://search.dongting.com/song/search?page=";
	private String get_songb = "&size=50&q=";
	private int page = 1;
	private int type = 0;// 0首次进入，1是刷新，2还是加载rivate int type = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_net_list_song, null);
		init(view);
		refrehView();
		return view;
	}

	public void init(View view) {
		tv_totalCount = (TextView) view.findViewById(R.id.tv_totalCount);
		net_song_list = (ListView) view.findViewById(R.id.list_net_list);
		pull = (PullToRefreshView) view.findViewById(R.id.pull);
		pull.setOnHeaderRefreshListener(this);
		pull.setOnFooterLoadListener(this);
		adapter = new SongNetListAdapter(getActivity(), MusicResource.musicResourceUri);
		net_song_list.setAdapter(adapter);
		net_song_list.setOnItemClickListener(this);
		requestQueue = Volley.newRequestQueue(getActivity());
	}

	public void refrehView() {
		if (type == 0 && MusicResource.netWorkState != 0) {
			pd = new ProgressDialog(getActivity());
			pd.setMessage("正在加载……");
			pd.show();
		}
		addList(get_songa + page + get_songb + MyTools.toUnicode(MusicResource.keywords));
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
					SearchSong ss = gson.fromJson(arg0, SearchSong.class);
					addListSearchSong(ss);
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
		if (type == 1) {
			pull.onHeaderRefreshFinish();
		} else if (type == 2) {
			pull.onFooterLoadFinish();
		}
	}

	/**
	 * 保存搜索得到的资源
	 *
	 * @param Json字符串
	 */
	public void addListSearchSong(SearchSong ss) {
		MusicResource.searchSongList.clear();
		if (ss.getData() != null) {
			for (int i = 0; i < ss.getData().size(); i++) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("songId", ss.getData().get(i).getSongId());
				dataMap.put("name", ss.getData().get(i).getName());
				dataMap.put("singerName", ss.getData().get(i).getSingerName());
				dataMap.put("albumName", ss.getData().get(i).getAlbumName());
				if (ss.getData().get(i).getAuditionList() != null) {
					List<Map<String, Object>> auditionList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < ss.getData().get(i).getAuditionList().size(); j++) {
						Map<String, Object> songMap = new HashMap<String, Object>();
						songMap.put("bitRate", ss.getData().get(i).getAuditionList().get(j).getBitRate());
						songMap.put("duration", ss.getData().get(i).getAuditionList().get(j).getDuration());
						songMap.put("size", ss.getData().get(i).getAuditionList().get(j).getSize());
						songMap.put("suffix", ss.getData().get(i).getAuditionList().get(j).getSuffix());
						songMap.put("url", ss.getData().get(i).getAuditionList().get(j).getUrl());
						songMap.put("typeDescription",
								ss.getData().get(i).getAuditionList().get(j).getTypeDescription());
						auditionList.add(songMap);
					}
					dataMap.put("auditionList", auditionList);
				}
				if (ss.getData().get(i).getUrlList() != null) {
					List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < ss.getData().get(i).getUrlList().size(); j++) {
						Map<String, Object> songMap = new HashMap<String, Object>();
						songMap.put("bitRate", ss.getData().get(i).getUrlList().get(j).getBitRate());
						songMap.put("duration", ss.getData().get(i).getUrlList().get(j).getDuration());
						songMap.put("size", ss.getData().get(i).getUrlList().get(j).getSize());
						songMap.put("suffix", ss.getData().get(i).getUrlList().get(j).getSuffix());
						songMap.put("url", ss.getData().get(i).getUrlList().get(j).getUrl());
						songMap.put("typeDescription", ss.getData().get(i).getUrlList().get(j).getTypeDescription());
						urlList.add(songMap);
					}
					dataMap.put("urlList", urlList);
				}
				if (ss.getData().get(i).getLlList() != null) {
					List<Map<String, Object>> llList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < ss.getData().get(i).getLlList().size(); j++) {
						Map<String, Object> songMap = new HashMap<String, Object>();
						songMap.put("bitRate", ss.getData().get(i).getLlList().get(j).getBitRate());
						songMap.put("duration", ss.getData().get(i).getLlList().get(j).getDuration());
						songMap.put("size", ss.getData().get(i).getLlList().get(j).getSize());
						songMap.put("suffix", ss.getData().get(i).getLlList().get(j).getSuffix());
						songMap.put("url", ss.getData().get(i).getLlList().get(j).getUrl());
						songMap.put("typeDescription", ss.getData().get(i).getLlList().get(j).getTypeDescription());
						llList.add(songMap);
					}
					dataMap.put("llList", llList);
				}
				if (ss.getData().get(i).getMvList() != null) {
					List<Map<String, Object>> mvList = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < ss.getData().get(i).getMvList().size(); j++) {
						Map<String, Object> songMap = new HashMap<String, Object>();
						songMap.put("videoId", ss.getData().get(i).getMvList().get(j).getVideoId());
						songMap.put("picUrl", ss.getData().get(i).getMvList().get(j).getPicUrl());
						songMap.put("durationMilliSecond",
								ss.getData().get(i).getMvList().get(j).getDurationMilliSecond());
						songMap.put("duration", ss.getData().get(i).getMvList().get(j).getDuration());
						songMap.put("bitRate", ss.getData().get(i).getMvList().get(j).getBitRate());
						songMap.put("path", ss.getData().get(i).getMvList().get(j).getPath());
						songMap.put("size", ss.getData().get(i).getMvList().get(j).getSize());
						songMap.put("suffix", ss.getData().get(i).getMvList().get(j).getSuffix());
						songMap.put("horizontal", ss.getData().get(i).getMvList().get(j).getHorizontal());
						songMap.put("vertical", ss.getData().get(i).getMvList().get(j).getVertical());
						songMap.put("url", ss.getData().get(i).getMvList().get(j).getUrl());
						songMap.put("type", ss.getData().get(i).getMvList().get(j).getType());
						songMap.put("typeDescription", ss.getData().get(i).getMvList().get(j).getTypeDescription());
						mvList.add(songMap);
					}
					dataMap.put("mvList", mvList);
					dataMap.put("picUrl", ss.getData().get(i).getPicUrl());
				}
				if (ss.getData().get(i).getSingers() != null) {
					List<Map<String, Object>> singers = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < ss.getData().get(i).getSingers().size(); j++) {
						Map<String, Object> songMap = new HashMap<String, Object>();
						songMap.put("singerId", ss.getData().get(i).getSingers().get(j).getSingerId());
						songMap.put("singerName", ss.getData().get(i).getSingers().get(j).getSingerName());
						singers.add(songMap);
					}
					dataMap.put("singers", singers);
				}
				MusicResource.searchSongList.add(dataMap);
			}
			Map<String, Object> searchSong = new HashMap<String, Object>();
			searchSong.put("pageCount", ss.getPageCount());
			searchSong.put("totalCount", ss.getTotalCount());
			MusicResource.searchSongList.add(searchSong);
			getList();
		}
	}

	/**
	 * 给list添加数据
	 */
	public void getList() {
		tv_totalCount.setText("已经为你找到"
				+ MusicResource.searchSongList.get(MusicResource.searchSongList.size() - 1).get("totalCount") + "相关歌曲");
		mList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < MusicResource.searchSongList.size() - 1; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			int index = i + 1 + (page - 1) * 50;
			map.put("order", index + "");
			if (MusicResource.searchSongList.get(i).get("picUrl") == null) {
				map.put("picUrl", "");
			} else {
				map.put("picUrl", MusicResource.searchSongList.get(i).get("picUrl"));
			}
			if (MusicResource.searchSongList.get(i).get("name") == null) {
				map.put("tilte", "");
			} else {
				map.put("tilte", MusicResource.searchSongList.get(i).get("name"));

			}
			if (MusicResource.searchSongList.get(i).get("singerName") == null) {
				map.put("artist", "");
			} else {
				map.put("artist", MusicResource.searchSongList.get(i).get("singerName"));
			}
			List<Map<String, Object>> lis = (List<Map<String, Object>>) MusicResource.searchSongList.get(i)
					.get("auditionList");
			if (lis.size() == 0) {
				continue;
			} else {
				map.put("url", lis.get(0).get("url"));
			}
			if (MusicResource.searchSongList.get(i).get("songId") == null) {
				map.put("songId", "");
			} else {
				map.put("songId", MusicResource.searchSongList.get(i).get("songId"));
			}
			mList.add(map);
		}
		setAdapters();
	}

	public void setAdapters() {
		if (type == 0) {
			adapter.setData(mList);// 第一次进入，加载第一次的数据，覆盖原来的空列表，此方法在适配器中
		} else if (type == 1) {
			adapter.setData(mList);// 刷新，新的数据覆盖旧得数据，此方法在适配器中
			pull.onHeaderRefreshFinish();
		} else {
			adapter.addData(mList);// 加载数据，续借在原来数据后面，此方法在适配器中
			pull.onFooterLoadFinish();
		}
		if (type == 0) {
			pd.dismiss();// 首次进入就显示一次
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("playerservice");
		if (MusicResource.musicResource != MusicResource.musicResourceUri) {
			MusicResource.musicResource = MusicResource.musicResourceUri;
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
	public void onFooterLoad(PullToRefreshView view) {
		// TODO Auto-generated method stub
		page += 1;
		type = 2;
		refrehView();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		page = 1;
		type = 1;
		refrehView();
	}

}
