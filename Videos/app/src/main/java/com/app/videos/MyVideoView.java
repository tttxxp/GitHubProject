package com.app.videos;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by x on 15-12-2.
 */
public class MyVideoView extends VideoView{
    private int mVideoWidth;
    private int mVideoHeight;
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
