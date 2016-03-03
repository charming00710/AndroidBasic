package com.example.beeplayer.bs.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取本地和网络资源信息
 *
 * @author Administrator
 *
 */
public class MusicResource {
	// 每周新歌试听列表
	public static List<Map<String, Object>> musicResourceWeek = new ArrayList<Map<String, Object>>();
	// 最近播放列表
	public static List<Map<String, Object>> musicResourceLatest = new ArrayList<Map<String, Object>>();
	// 点击搜索按钮时通过网络搜索得到的资源
	public static List<Map<String, Object>> searchSongList = new ArrayList<Map<String, Object>>();
	// 临时list资源空间
	public static List<Map<String, Object>> musicResourceTemp = new ArrayList<Map<String, Object>>();
	// 专辑的list 包含key有专辑名special，歌曲sum
	public static List<Map<String, Object>> musicResourceSpecial = new ArrayList<Map<String, Object>>();
	// 歌手的list 包含key有歌手songer，歌曲sum
	public static List<Map<String, Object>> musicResourceSonger = new ArrayList<Map<String, Object>>();
	// musicResourceLocal存储本地资源文件
	public static List<Map<String, Object>> musicResourceLocal = new ArrayList<Map<String, Object>>();
	// musicResourceUri网络资源
	public static List<Map<String, Object>> musicResourceUri = new ArrayList<Map<String, Object>>();
	public static List<Map<String, Object>> musicResource = new ArrayList<Map<String, Object>>();

	// 循环模式 0列表循环1顺序播放2随机播放3单曲循环
	public static int looping = Constants.MPM_ORDER_PLAY;// 默认1顺序播放
	// 播放音乐的id
	public static int music_id = Constants.MPR_INDEX;// 默认0
	// 控制信号变量
	public static int play_control = Constants.MPK_NOREID;
	// temp临时资源标记 0是songer，1是special，2是network
	public static int temp_index;
	// 歌曲播放时的数据
	public static int duration = Constants.SONG_DURATION;
	public static int induration = Constants.SONG_DURATION;
	public static String song = Constants.SONG;
	public static String songer = Constants.SONGER;
	// 标记当前网络状态 0没有网络，1Wifi网络，2 3G网络
	public static int netWorkState = Constants.TYPE_NO;
	// 点击搜索按钮时的关键字
	public static String keywords = new String();
	// webview请求网址
	public static String albumid = new String();

}
