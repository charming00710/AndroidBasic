package com.example.beeplayer.bs.activity;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		initImageLoader();
		super.onCreate();
	}

	public void initImageLoader() {
		String cachDir = "";
		if(getExternalCacheDir()!=null){
			cachDir = getExternalCacheDir().toString();
		}else {
			cachDir = getCacheDir().toString();
		}
		File cacheDir = StorageUtils.getOwnCacheDirectory(this, cachDir+"/"+"bpcach");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置当前线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 缓存显示不同大小的同一张图片
				.diskCacheSize(50 * 1024 * 1024)// 50 Mb sd卡(本地)缓存的最大值
				.diskCache(new UnlimitedDiskCache(cacheDir))// sd卡缓存
				.memoryCache(new WeakMemoryCache())// 内存缓存
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}
}

