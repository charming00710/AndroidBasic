package com.example.beeplayer.bs.tools;

/**
 * 常量类 播放状态，播放模式
 *
 * @author Administrator
 *
 */
public class Constants {

	// 播放状态
	public static final int MPS_NOFILE = -1; // 无音乐文件
	public static final int MPS_INVALID = 0; // 当前音乐文件无效
	public static final int MPS_PREPARE = 1; // 准备就绪
	public static final int MPS_PLAYING = 2; // 播放中
	public static final int MPS_PAUSE = 3; // 暂停

	// 播放模式
	public static final int MPM_LIST_LOOP_PLAY = 0; // 列表循环
	public static final int MPM_ORDER_PLAY = 1; // 顺序播放
	public static final int MPM_RANDOM_PLAY = 2; // 随机播放
	public static final int MPM_SINGLE_LOOP_PLAY = 3; // 单曲循环
	// 播放资源种类，网络还是sd
	public static final int MPR_LOCAL = 0;// SD卡播放
	public static final int MPR_NETWORK = 1;// 网络播放
	// 播放键状态 记录播放器播放状态0x1没有加载文件0x2播放中0x3暂停中0x4停止0x5上一曲0x6下一曲
	public static final int MPK_NOREID = 0x1;
	public static final int MPK_PLAYING = 0x2;
	public static final int MPK_PAUSE = 0x3;
	public static final int MPK_STOP = 0x4;
	public static final int MPK_LAST = 0x5;
	public static final int MPK_NEXT = 0x6;
	public static final int MPK_OTHER = 0x7;// 跳转进度
	public static final int MPK_OTHER1 = 0x8;// 跳转进度
	// 资源下标
	public static final int MPR_INDEX = 0;
	// temp临时资源标记 0是songer，1是special，2是network
	public static final int TEMP_SONGER = 0;
	public static final int TEMP_SPECIAL = 1;
	public static final int TEMP_NETWORK = 2;
	// 歌曲播放时间默认值
	public static final int SONG_DURATION = 0;
	// 歌曲播放默认无歌曲
	public static final String SONG = "歌曲：未知";
	public static final String SONGER = "演唱：未知";
	// 标记当前网络状态 0没有网络，1Wifi网络，2 3G网络
	public static final int TYPE_NO = 0;
	public static final int TYPE_WIFI = 1;
	public static final int TYPE_MOBILE = 2;

}
