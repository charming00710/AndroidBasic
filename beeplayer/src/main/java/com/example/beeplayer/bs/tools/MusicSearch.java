package com.example.beeplayer.bs.tools;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.beeplayer.bs.resource.MusicResource;

import java.io.File;
import java.util.HashMap;

/**
 * 音乐信息扫描
 *
 * @author Administrator
 *
 */
public class MusicSearch {

	/**
	 * 从系统数据库中得到music文件信息
	 *
	 * @param ctx
	 */
	public void getMusicFile(Context ctx) {
		MusicResource.musicResourceLocal.clear();
		Cursor cursor = ctx.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.AudioColumns.TITLE);// 查询的表和排序方式
		// 循环输出歌曲的信息
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, Object> nowMap = new HashMap<String, Object>();
				// 歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
				int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				nowMap.put("duration", duration);
				// 歌曲文件的路径 ：MediaStore.Audio.Media.DATA
				String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				nowMap.put("path", path);
				// 歌曲的名称 ：MediaStore.Audio.Media.TITLE
				String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				nowMap.put("tilte", tilte);
				// 歌曲的专辑名：MediaStore.Audio.Media.ALBUM
				String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				nowMap.put("album", album);
				// 歌曲的歌手名： MediaStore.Audio.Media.ARTIST
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
				nowMap.put("artist", artist);
				MusicResource.musicResourceLocal.add(nowMap);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	/**
	 * 找出歌手相同的文件
	 */
	public void getSonger(Context ctx) {
		MusicResource.musicResourceSonger.clear();
		Cursor cursor = ctx.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Artists.ARTIST);// 查询的表和排序方式
		// 循环输出歌曲的信息
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, Object> nowMap = new HashMap<String, Object>();
				// 歌曲的歌手名： MediaStore.Audio.Media.ARTIST
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
				nowMap.put("artist", artist);
				// 歌曲的数量 ：MediaStore.Audio.Media.DURATION
				int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
				nowMap.put("number", duration);
				MusicResource.musicResourceSonger.add(nowMap);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	/**
	 * 找出专辑相同的文件
	 */
	public void getSpecial(Context ctx) {
		MusicResource.musicResourceSpecial.clear();
		Cursor cursor = ctx.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Albums.ALBUM);// 查询的表和排序方式
		// 循环输出歌曲的信息
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, Object> nowMap = new HashMap<String, Object>();
				// 歌曲的专辑名：MediaStore.Audio.Media.ALBUM
				String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
				nowMap.put("album", album);
				// 歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
				int number = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
				nowMap.put("number", number);
				MusicResource.musicResourceSpecial.add(nowMap);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	/**
	 * 删除歌曲
	 *
	 */
	public void deletsong(Context ctx, int index) {
		String path = MusicResource.musicResourceLocal.get(index).get("path").toString();
		MusicResource.musicResourceLocal.remove(index);
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			Uri data = Uri.parse("file://" + path);
			ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
			Toast.makeText(ctx, "歌曲删除成功!", Toast.LENGTH_SHORT).show();
		}
	}
}
