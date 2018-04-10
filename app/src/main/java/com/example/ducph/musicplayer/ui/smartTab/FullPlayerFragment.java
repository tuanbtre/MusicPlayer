package com.example.ducph.musicplayer.ui.smartTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ducph.musicplayer.R;
import com.example.ducph.musicplayer.ui.MusicPlayerActivity;
import com.example.ducph.musicplayer.utils.LogHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullPlayerFragment extends Fragment {


    public FullPlayerFragment() {
        // Required empty public constructor
    }
    private View view;
    private TextView mLine1;
    private TextView mLine2;
    public TextView mLine3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view!=null)
            return view;
        view = inflater.inflate(R.layout.fragment_full_player, container, false);
        mLine1 = (TextView) view.findViewById(R.id.line1);
        mLine2 = (TextView) view.findViewById(R.id.line2);
        mLine3 = (TextView) view.findViewById(R.id.line3);
        updateFromParams(getArguments());
        return view;
    }
    private void updateFromParams(Bundle bundle) {
        if (bundle != null) {
            MediaDescriptionCompat description = bundle.getParcelable(
                    MusicPlayerActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION);
            String lyric = bundle.getString( MusicPlayerActivity.EXTRA_CURRENT_MEDIA_LYRIC);
            if (description != null) {
                updateMediaDescription(description,lyric);
            }
        }
    }

    public void updateMediaDescription(MediaDescriptionCompat description,String lyric) {
        if (description == null) {
            return;
        }
        LogHelper.d(getClass().getSimpleName(), "updateMediaDescription called ");

        mLine1.setText(description.getTitle());
        mLine2.setText(description.getSubtitle());
//        fetchImageAsync(description);
    }


}
