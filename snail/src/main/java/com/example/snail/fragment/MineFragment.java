package com.example.snail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snail.MainActivity;
import com.example.snail.R;

/**
 * Created by zcm on 2016/2/26.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private ImageView headBgImg;

    private TextView playListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        headBgImg = (ImageView) view.findViewById(R.id.mine_bg_head);
        headBgImg.setImageResource(R.drawable.mine_bg_head);

        playListView = (TextView) view.findViewById(R.id.textview_play_list);
        playListView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.textview_play_list:
                ((MainActivity) getActivity()).startFragment( new LocalMusicFragment() );
                break;
        }
    }
}
