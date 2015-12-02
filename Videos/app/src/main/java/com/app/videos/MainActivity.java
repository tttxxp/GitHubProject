package com.app.videos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

/**
 * Displays a {@link GalleryFragment}.
 */
public class MainActivity extends FragmentActivity {

    public static final int HIDE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Utils.toggleHideyBar(this);

        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility != 6) {
                    getWindow().getDecorView().setSystemUiVisibility(HIDE);
                }
            }
        });
    }
}
