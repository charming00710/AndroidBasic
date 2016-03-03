package com.example.beeplayer.bs.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.json.Searchkeyword;
import com.example.beeplayer.bs.resource.MusicResource;
import com.example.beeplayer.bs.tools.MyTools;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 搜索界面输入搜索内容显示搜索结果，并将其添加至ListView中
 */
public class SearchFragment extends Fragment implements TextWatcher, OnClickListener, OnItemClickListener {
	private RelativeLayout lin_null;
	private ListView lv_search;
	private EditText et_search;
	private ImageView image_exit, image_search;
	private SimpleAdapter adapter;
	private List<HashMap<String, String>> hint_list;
	// 网络资源请求地址 键盘输入提示请求
	private String get_hit = "http://so.ard.iyyin.com/sug/sugAll?q=";
	private RequestQueue requestQueue;
	private FragmentManager fragmentManager;
	private SearchShowFragment ssf;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_search, null);
		init(view);
		return view;
	}

	/**
	 * 初始化控件
	 */
	public void init(View view) {
		lin_null = (RelativeLayout) view.findViewById(R.id.lin_null);
		lin_null.setOnClickListener(this);
		lv_search = (ListView) view.findViewById(R.id.lv_serch);
		lv_search.setOnItemClickListener(this);
		et_search = (EditText) view.findViewById(R.id.et_search);
		et_search.addTextChangedListener(this);
		image_exit = (ImageView) view.findViewById(R.id.image_exit);
		image_exit.setOnClickListener(this);
		image_search = (ImageView) view.findViewById(R.id.image_search);
		image_search.setOnClickListener(this);
		hint_list = new ArrayList<HashMap<String, String>>();
		adapter = new SimpleAdapter(getActivity(), hint_list, R.layout.search_adapter, new String[] { "2" },
				new int[] { R.id.tv_search });
		requestQueue = Volley.newRequestQueue(getActivity());
		fragmentManager = getChildFragmentManager();
	}

	/**
	 * 点击搜索方法
	 */
	public void startsearch() {
		fragmentManager.popBackStackImmediate();
		ssf = null;
		MusicResource.keywords = et_search.getText().toString();
		ssf = new SearchShowFragment();
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_search, ssf);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		et_search.setText(hint_list.get(position).get("2"));
		startsearch();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.image_exit:
				getActivity().getSupportFragmentManager().popBackStack();
				break;
			// 歌曲搜索
			case R.id.image_search:
				startsearch();
				break;
			default:
				break;
		}
	}

	/**
	 * 输入内容得到提示关键字
	 *
	 * @param url
	 *
	 * @return String 类型的结果 若请求失败则返回null
	 */
	public void getKeyword(String url) {
		if (MusicResource.netWorkState != 0) {

			StringRequest request = new StringRequest(url, new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					Searchkeyword sk = gson.fromJson(arg0, Searchkeyword.class);
					addListKeyWord(sk);
					if (hint_list == null) {
						adapter.notifyDataSetChanged();
					} else {
						lv_search.setAdapter(adapter);
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					getDialog("可恨的网络啊……");
				}
			});
			requestQueue.add(request);
		} else {
			getDialog("网络好像有点……");
		}
	}

	public void getDialog(String hit) {
		Dialog dialog = new Dialog(getActivity(), R.style.list_song_dialog);
		dialog.setTitle(hit);
		dialog.show();
	}

	/**
	 * 添加keyword数据到list
	 *
	 * @param sk
	 *            实体类对象
	 */
	public void addListKeyWord(Searchkeyword sk) {
		if (sk.getData().getKeyword() != null) {

			for (int i = 0; i < sk.getData().getKeyword().size(); i++) {
				HashMap<String, String> keyMap = new HashMap<String, String>();
				keyMap.put("1", sk.getData().getKeyword().get(i).getHit() + "");
				keyMap.put("2", sk.getData().getKeyword().get(i).getVal());
				keyMap.put("3", sk.getData().getKeyword().get(i).getHot() + "");
				hint_list.add(keyMap);
			}
		}
		if (sk.getData().getSong() != null) {

			for (int i = 0; i < sk.getData().getSong().size(); i++) {
				HashMap<String, String> songMap = new HashMap<String, String>();
				songMap.put("1", sk.getData().getSong().get(i).get_id() + "");
				songMap.put("2", sk.getData().getSong().get(i).getName());
				songMap.put("3", sk.getData().getSong().get(i).getSinger_name());
				hint_list.add(songMap);
			}
		}
		if (sk.getData().getSinger() != null) {

			for (int i = 0; i < sk.getData().getSinger().size(); i++) {
				HashMap<String, String> singerMap = new HashMap<String, String>();
				singerMap.put("1", sk.getData().getSinger().get(i).get_id() + "");
				singerMap.put("2", sk.getData().getSinger().get(i).getAlias_name());
				singerMap.put("3", sk.getData().getSinger().get(i).getSinger_name());
				hint_list.add(singerMap);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		if (count == 1) {
			if (fragmentManager.popBackStackImmediate()) {
				ssf = null;
			}
		}
	}

	/**
	 * 监听输入框内容,当输入框内容改变时请求数据
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

		hint_list.clear();
		if (s.equals("")) {
			adapter.notifyDataSetChanged();

		} else {
			getKeyword(get_hit + MyTools.toUnicode(s.toString()));
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
