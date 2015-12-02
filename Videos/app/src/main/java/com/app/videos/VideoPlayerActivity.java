package com.app.videos;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;


/**
 * Created by lk on 11/3/15.
 */
public class VideoPlayerActivity extends Activity implements View.OnTouchListener, MediaPlayer.OnPreparedListener {

    private static final String TAG = "VideoPlayer";
    private VideoView mVideoView;
    private Intent mIntent;
    private String mPlayPath;
    private int mPos = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.toggleHideyBar(this);
        setContentView(R.layout.video_player_layout);
        getData();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mVideoView != null){
            if(mPos > 0){
                mVideoView.seekTo(mPos);
                mPos = 0;
            }
            mVideoView.start();
        }
    }

    private void getData() {
        mIntent = getIntent();
        mPlayPath = mIntent.getStringExtra("uri");
        Log.d("TAG","==LK== path = " + mPlayPath);

    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_player_view);
        mVideoView.setOnTouchListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setVideoPath(mPlayPath);
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onStop() {
        mVideoView.pause();
        mPos = mVideoView.getCurrentPosition();
        Log.d(TAG,"==LK== cur pos = " + mPos);
        super.onStop();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.finish();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
    }

}