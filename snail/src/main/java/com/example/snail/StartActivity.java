package com.example.snail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.snail.service.PlayerService;
import com.example.snail.utils.MusicResource;
import com.example.snail.utils.MusicSearch;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zcm on 2016/2/23.
 */
public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_start );

        MusicResource.clearLocalMusic();
        MusicResource.addMusicToLocalList(MusicSearch.getLocalMusic(this));

        Intent playServiceIntent = new Intent(this, PlayerService.class);
        startService(playServiceIntent);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }

}
