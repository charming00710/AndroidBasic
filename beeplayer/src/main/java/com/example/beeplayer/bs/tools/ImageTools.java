package com.example.beeplayer.bs.tools;

import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.example.beeplayer.bs.R;
import com.example.beeplayer.bs.resource.MusicResource;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageTools {

	public static void setImageUrl(String url, ImageView image) {
		if (MusicResource.netWorkState != 0) {

			DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_empty)
					// 设置图片在下载期间显示的图片
					.showImageForEmptyUri(R.drawable.img_empty)
					// 设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(R.drawable.img_empty)
					// 设置图片加载/解码过程中错误时候显示的图片
					.cacheInMemory(true)
					// 设置下载的图片是否缓存在内存中
					.cacheOnDisk(true)
					// 设置下载的图片是否缓存在SD卡中
					.bitmapConfig(Config.ALPHA_8)// 图片质量
					.displayer(new FadeInBitmapDisplayer(1000))// 图片加载好后渐入的动画时间
					.build();
			ImageLoader.getInstance().displayImage(url, image, options);
		}
	}
	public static void setImageUrlH(String url, ImageView image) {
		if (MusicResource.netWorkState != 0) {

			DisplayImageOptions options = new DisplayImageOptions.Builder()
//					.showImageOnLoading(R.drawable.img_empty)
					// 设置图片在下载期间显示的图片
//					.showImageForEmptyUri(R.drawable.img_empty)
					// 设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(R.drawable.xiaomifeng)
					// 设置图片加载/解码过程中错误时候显示的图片
					.cacheInMemory(true)
					// 设置下载的图片是否缓存在内存中
					.cacheOnDisk(true)
					// 设置下载的图片是否缓存在SD卡中
					.bitmapConfig(Config.ARGB_8888)// 图片质量
					.displayer(new FadeInBitmapDisplayer(1000))// 图片加载好后渐入的动画时间
					.build();
			ImageLoader.getInstance().displayImage(url, image, options);
		}
	}
}
