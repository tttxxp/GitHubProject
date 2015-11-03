package com.app.videos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Displays a {@link GalleryFragment}.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
