package com.example.snail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.snail.R;
import com.example.snail.utils.MusicResource;
import com.example.snail.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaoming on 2016/4/6.
 */
public class SongListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView songListView;

    private List<Map<String, String>> songInfoList;

    private SimpleAdapter adapter;

    private PullToRefreshView pull;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_song_list, null );

        init(view);

        intData();
        return view;
    }



    private void init(View view) {
        songListView = (ListView) view.findViewById(R.id.list_mine);

        songInfoList = new ArrayList<Map<String, String>>();

        adapter = new SimpleAdapter(getActivity(), songInfoList, R.layout.adapter_song_list, new String[]{"order", "song", "songer"},
                new int[]{R.id.tv_order, R.id.tv_song, R.id.tv_songer});

        songListView.setAdapter(adapter);
        songListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void intData() {
        songInfoList.clear();
        for ( int i=0; i< MusicResource.getLocalMusicList().size(); i++ ) {
            Map<String, String> songInfo = new HashMap<String, String>();
            songInfo.put("order", i+1 + "");
            songInfo.put("song", MusicResource.getLocalMusicList().get(i).getTitle());
            songInfo.put("songer", MusicResource.getLocalMusicList().get(i).getArtist());
            songInfoList.add(songInfo);
        }

        adapter.notifyDataSetChanged();
    }
}
