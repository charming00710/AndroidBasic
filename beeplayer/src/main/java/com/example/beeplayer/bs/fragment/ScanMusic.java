package com.example.beeplayer.bs.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beeplayer.bs.R;

import java.io.File;

public class ScanMusic extends Fragment implements OnClickListener {
	private ImageView img_scanMusci;
	private TextView tv_showpath;
	private TextView tv_num;
	private int number = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scanmusic, null);
		init(view);
		return view;
	}

	public void init(View view) {
		tv_showpath = (TextView) view.findViewById(R.id.tv_showpath);
		tv_num = (TextView) view.findViewById(R.id.tv_num);
		img_scanMusci = (ImageView) view.findViewById(R.id.img_scanMusci);
		img_scanMusci.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		number = 0;
		folderScan(getActivity(), Environment.getExternalStorageDirectory().toString());

	}

	/**
	 * 通过发送下面的Intent启动MediaScanner服务扫描指定的目录
	 *
	 * @param ctx
	 * @param dir
	 */
	public void fileScan(Context ctx, String file) {
		Uri data = Uri.parse("file://" + file);
		ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
	}

	/**
	 * 扫描文件夹
	 *
	 * @param ctx
	 * @param path
	 */
	public void folderScan(Context ctx, String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] array = file.listFiles();
			for (int i = 0; i < array.length; i++) {
				File f = array[i];
				if (f.isFile()) {// FILE TYPE
					String name = f.getName();
					if (name.contains(".mp3")) {
						fileScan(ctx, f.getAbsolutePath());
						tv_showpath.setText(name);
						number++;
						tv_num.setText("共找到：" + number + "首歌曲");
					}
				} else {// FOLDER TYPE
					folderScan(ctx, f.getAbsolutePath());
				}
			}
		}
	}

}
