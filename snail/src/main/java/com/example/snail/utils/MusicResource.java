package com.example.snail.utils;

import com.example.snail.domain.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcm on 2016/2/24.
 */
public class MusicResource {
    private static final List<Music> localMusicList = new ArrayList<Music>();


    public static void addMusicToLocalList(Music music) {
        localMusicList.add(music);
    }

    public static void addMusicToLocalList(List<Music> musicList) {
        localMusicList.addAll(musicList);
    }

    public static void clearLocalMusic() {
        localMusicList.clear();
    }

    public static List<Music> getLocalMusicList() {
        return localMusicList;
    }
}
