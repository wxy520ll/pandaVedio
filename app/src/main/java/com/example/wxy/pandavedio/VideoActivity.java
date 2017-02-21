package com.example.wxy.pandavedio;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.wxy.pandavedio.view.FullScreenVideoView;

/**
 * Created by wxy on 2017/2/18.
 */

public class VideoActivity extends Activity {
    FullScreenVideoView fullScreenVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        fullScreenVideoView= (FullScreenVideoView) findViewById(R.id.video);
      //  fullScreenVideoView.setVideoPath("http://183.2.242.222:8090/vip/1.mp4");
        fullScreenVideoView.setVideoURI(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
        //设置视频控制器
        fullScreenVideoView.setMediaController(new android.widget.MediaController(this));

        fullScreenVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        fullScreenVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });

        fullScreenVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            Intent mIntent = new Intent();
            mIntent.putExtra("url", "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            this.setResult(RESULT_OK,mIntent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
