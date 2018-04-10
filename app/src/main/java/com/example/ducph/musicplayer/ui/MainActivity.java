package com.example.ducph.musicplayer.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ducph.musicplayer.R;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeToolbar();
        setTitle(getString(R.string.drawer_home_title));
    }
}
