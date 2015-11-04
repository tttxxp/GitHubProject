package com.app.videos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

/**
 * Created by lk on 11/3/15.
 */
public class VideoPlayerActivity extends Activity implements View.OnTouchListener {

    private VideoView mVideoView;
    private Intent mIntent;
    private String mPlayPath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_layout);
        getData();
        initView();
    }

    private void getData() {
        mIntent = getIntent();
        mPlayPath = mIntent.getStringExtra("uri");
        android.util.Log.d("TAG"," == lk== path = " + mPlayPath);

    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_player_view);
        mVideoView.setOnTouchListener(this);
        mVideoView.setVideoPath(mPlayPath);
        mVideoView.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.finish();
        return false;
    }
}