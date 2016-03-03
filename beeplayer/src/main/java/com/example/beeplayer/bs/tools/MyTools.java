package com.example.beeplayer.bs.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MyTools {
	// 把网址URL编码转义
	public static String toUnicode(String s) {
		try {
			s = URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 时间毫秒转分钟
	 */
	public static String getMinute(int time) {
		int h = time / (1000 * 60 * 60);
		String minute;
		if (h == 0) {
			minute = time % (1000 * 60 * 60) / (1000 * 60) + ":" + (time % (1000 * 60)) / 1000;
		} else {

			minute = h + ":" + time % (1000 * 60 * 60) / (1000 * 60) + ":" + (time % (1000 * 60)) / 1000;
		}
		return minute;
	}
}
