package com.example.ducph.musicplayer.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ducph.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LyricFragment extends Fragment {


    public LyricFragment() {
        // Required empty public constructor
    }
    private View view;
    private TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view!=null)
            return view;
        view = inflater.inflate(R.layout.fragment_lyric, container, false);
         text = (TextView) view.findViewById(R.id.text);
        updateMediaDescription(getArguments().getString(MusicPlayerActivity.EXTRA_CURRENT_MEDIA_LYRIC));
        return view;
    }
    public void updateMediaDescription(String lyric){

        text.setText(Html.fromHtml(lyric));
    }
}
