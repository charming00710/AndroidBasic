package com.example.snail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.snail.R;

/**
 * Created by zcm on 2016/2/26.
 */
public class MineFragment extends Fragment {

    private ImageView headBgImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        headBgImg = (ImageView) view.findViewById(R.id.mine_bg_head);
        headBgImg.setImageResource(R.drawable.mine_bg_head);

        return view;
    }

}
