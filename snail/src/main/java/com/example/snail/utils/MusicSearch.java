package com.example.snail.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.snail.domain.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcm on 2016/2/24.
 */
public class MusicSearch {

    public static List<Music> getLocalMusic(Context ctx) {
        Cursor cursor = ctx.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.AudioColumns.TITLE);// 查询的表和排序方式
        List<Music> result = new ArrayList<Music>(cursor.getCount());

        while (cursor.moveToNext()) {
            Music music = new Music();
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            music.setDuration(duration);

            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            music.setPath(path);

            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            music.setTitle(title);

            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            music.setAlbum(album);

            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            music.setArtist(artist);

            result.add(music);
        }
        return result;
    }
}
