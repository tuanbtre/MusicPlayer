package com.example.ducph.musicplayer.ui.MyMusicScreen;

import android.app.SearchManager;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducph.musicplayer.R;
import com.example.ducph.musicplayer.ui.BaseActivity;
import com.example.ducph.musicplayer.ui.MediaBrowserFragment;
import com.example.ducph.musicplayer.ui.MyViewPager;
import com.example.ducph.musicplayer.ui.smartTab.FragmentPagerItem;
import com.example.ducph.musicplayer.ui.smartTab.FragmentPagerItemAdapter;
import com.example.ducph.musicplayer.ui.smartTab.FragmentPagerItems;
import com.example.ducph.musicplayer.ui.smartTab.SmartTabLayout;

public class MyMusicActivity extends BaseActivity implements MediaBrowserFragment.MediaFragmentListener {
    private FragmentPagerItemAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        initializeToolbar();
        setTitle(getString(R.string.drawer_my_music_title));
        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(getLayoutInflater().inflate(R.layout.smart_tab_layout,tab,false));
        MyViewPager myViewPager = (MyViewPager) findViewById(R.id.myViewPager);
        SmartTabLayout smartTabLayout = (SmartTabLayout) findViewById(R.id.view_pager_tab);
        FragmentPagerItems items = new FragmentPagerItems(this);
        items.add(FragmentPagerItem.of("Downloads",DownloadsFragment.class,new Bundle()));
        items.add(FragmentPagerItem.of("Songs",SongsFragment.class,new Bundle()));
        items.add(FragmentPagerItem.of("Albums",AlbumsFragment.class,new Bundle()));
        items.add(FragmentPagerItem.of("Artists",ArtistsFragment.class,new Bundle()));
        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),items);
        myViewPager.setAdapter(adapter);
        myViewPager.setOffscreenPageLimit(items.size());

        smartTabLayout.setViewPager(myViewPager);
    }

    @Override
    public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {

    }

    @Override
    public void setToolbarTitle(CharSequence title) {

    }
    @Override
    protected void onMediaControllerConnected() {
        Log.d("","onMediaControllerConnected");
        ((SongsFragment)adapter.getPage(1)).onConnected();
        ((AlbumsFragment)adapter.getPage(2)).onConnected();
    }
}
