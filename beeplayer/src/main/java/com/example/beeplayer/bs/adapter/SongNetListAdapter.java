package com.example.beeplayer.bs.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.json.SearchDown;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.ImageTools;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongNetListAdapter extends BaseAdapter {

	private List<Map<String, Object>> mList;
	private Context context;
	private List<Map<String, Object>> urllist;
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				Toast.makeText(context, "已添加下载任务", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "下载已完成,去扫描添加新歌曲吧！", 0).show();
			}
			return false;
		}
	});

	public SongNetListAdapter(Context context, List<Map<String, Object>> mList) {
		this.context = context;
		this.mList = mList;
		MusicResource.musicResourceUri = this.mList;
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

	// 下拉加载 ，使新加载的数据续在原来消息后面接着显示
	public void addData(List<Map<String, Object>> mList) {
		this.mList.addAll(mList);
		MusicResource.musicResourceUri = this.mList;
		notifyDataSetChanged();// //更新适配器
	}

	// 上拉加载 ，使新的数据覆盖旧得数据，然后更新
	public void setData(List<Map<String, Object>> mList) {
		this.mList = mList;
		MusicResource.musicResourceUri = this.mList;
		notifyDataSetChanged();// 更新适配器
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.net_list_song_adaptet, null);
			viewHolder.tv_order = (TextView) convertView.findViewById(R.id.tv_order);
			viewHolder.image_picUrl = (ImageView) convertView.findViewById(R.id.image_songer);
			viewHolder.tv_tilte = (TextView) convertView.findViewById(R.id.tv_song);
			viewHolder.tv_artist = (TextView) convertView.findViewById(R.id.tv_songer);
			viewHolder.image_down = (ImageView) convertView.findViewById(R.id.image_down);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map = mList.get(position);
		viewHolder.tv_order.setText(map.get("order").toString());
		ImageTools.setImageUrl(map.get("picUrl").toString(), viewHolder.image_picUrl);
		viewHolder.tv_tilte.setText(map.get("tilte").toString());
		viewHolder.tv_artist.setText(map.get("artist").toString());
		viewHolder.image_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getList(pos);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tv_order, tv_tilte, tv_artist;
		ImageView image_picUrl, image_down;
	}

	/**
	 * 下载提示框
	 */
	public void downloadMusic(final int pos) {

		Dialog dialog = new Dialog(context, R.style.list_song_dialog);
		dialog.setTitle("选择下载品质");
		View view = LayoutInflater.from(context).inflate(R.layout.fragment_list_downsong, null);
		ListView list_downsong = (ListView) view.findViewById(R.id.list_downsong);
		list_downsong.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tilte", mList.get(pos).get("tilte").toString());
				map.put("artist", mList.get(pos).get("artist").toString());
				map.put("url", urllist.get(position).get("url").toString());
				downMusic(map);
			}
		});
		SimpleAdapter adapter = new SimpleAdapter(context, urllist, R.layout.downsong,
				new String[] { "typeDescription", "size" }, new int[] { R.id.tv_type, R.id.tv_size });
		list_downsong.setAdapter(adapter);
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * 获取下载文件用的数据
	 *
	 * @param pos
	 *            item
	 */
	private void getList(final int pos) {
		if (MusicResource.netWorkState != 0) {
			if (MusicResource.netWorkState == 2) {
				Dialog dialog = new Dialog(context, R.style.list_song_dialog);
				dialog.setTitle("当前链接3G网络请谨慎下载");
				dialog.show();
			}
			RequestQueue requestQueue = Volley.newRequestQueue(context);
			String url = "http://api.dongting.com/song/song/" + mList.get(pos).get("songId").toString()
					+ "?utdid=VmTcb0HL5bcDAE%2BGZ6jySeZS";
			StringRequest request = new StringRequest(url, new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					SearchDown sd = gson.fromJson(arg0, SearchDown.class);
					getListDate(sd, pos);
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub

				}
			});
			requestQueue.add(request);
		}
	}

	/**
	 * 得到歌曲下载信息数据
	 *
	 * @param sd歌曲下载实体类
	 * @return
	 */
	protected void getListDate(SearchDown sd, int pos) {
		urllist = new ArrayList<Map<String, Object>>();
		if (sd.getMsg() != null) {
			for (int i = 0; i < sd.getData().getUrlList().size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("bitRate", sd.getData().getUrlList().get(i).getBitRate());
				map.put("duration", sd.getData().getUrlList().get(i).getDuration());
				float f = new BigDecimal(sd.getData().getUrlList().get(i).getSize() / 1048576.0f)
						.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				map.put("size", f + "M");
				map.put("suffix", sd.getData().getUrlList().get(i).getSuffix());
				map.put("url", sd.getData().getUrlList().get(i).getUrl());
				map.put("typeDescription", sd.getData().getUrlList().get(i).getTypeDescription());
				urllist.add(map);
			}
		}
		downloadMusic(pos);
	}

	/**
	 * 下载音乐文件的方法
	 *
	 * @param map
	 */
	public void downMusic(Map<String, Object> map) {
		String sd = "";
		if (Environment.getExternalStorageDirectory() != null) {
			sd = Environment.getExternalStorageDirectory().toString() + "/bplayer/music/";
			final String path = sd + map.get("tilte").toString() + "-" + map.get("artist") + ".mp3";
			final String url_str = map.get("url").toString();
			final File file = new File(path);
			if (file.exists()) {
				file.delete();
			} else {
				new File(sd).mkdirs();
			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					InputStream is = null;
					OutputStream os = null;
					try {
						URL url = new URL(url_str);
						URLConnection con = url.openConnection();
						is = con.getInputStream();
						byte[] by = new byte[1024];
						int len;
						os = new FileOutputStream(path);
						Message msgstart = new Message();
						msgstart.arg1 = 1;
						handler.sendMessage(msgstart);
						while ((len = is.read(by)) != -1) {
							os.write(by, 0, len);
						}
						Message msgstop = new Message();
						msgstop.arg1 = 0;
						handler.sendMessage(msgstop);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (os != null) {
								os.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							if (is != null) {
								is.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		} else {
			Toast.makeText(context, "SD卡无法访问", 0).show();
		}
	}

}
