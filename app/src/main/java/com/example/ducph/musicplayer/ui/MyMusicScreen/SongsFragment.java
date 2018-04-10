package com.example.ducph.musicplayer.ui.MyMusicScreen;


import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ducph.musicplayer.R;
import com.example.ducph.musicplayer.ui.MediaBrowserFragment;
import com.example.ducph.musicplayer.ui.MediaItemViewHolder;
import com.example.ducph.musicplayer.utils.LogHelper;
import com.example.ducph.musicplayer.utils.MediaIDHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {


    public SongsFragment() {
        // Required empty public constructor
    }
    private MediaBrowserFragment.MediaFragmentListener mMediaFragmentListener;
    private BrowseAdapter mBrowserAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_songs, container, false);
        mBrowserAdapter = new BrowseAdapter(getActivity());

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(mBrowserAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MediaBrowserCompat.MediaItem item = mBrowserAdapter.getItem(position);
                getActivity().getSupportMediaController().getTransportControls()
                        .playFromMediaId(item.getMediaId(), null);
            }
        });
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // If used on an activity that doesn't implement MediaFragmentListener, it
        // will throw an exception as expected:
        mMediaFragmentListener = (MediaBrowserFragment.MediaFragmentListener) activity;
    }
    private static final String TAG = LogHelper.makeLogTag(SongsFragment.class);
    private String mMediaId;
    @Override
    public void onStart() {
        super.onStart();

        // fetch browsing information to fill the listview:
        MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();

        LogHelper.d(TAG, "fragment.onStart, mediaId=", mMediaId,
                "  onConnected=" + mediaBrowser.isConnected());

        if (mediaBrowser.isConnected()) {
            onConnected();
        }


    }
    public void onConnected() {
        if (isDetached()) {
            return;
        }
        mMediaId = null;
        if (mMediaId == null) {
            mMediaId = MediaIDHelper.MEDIA_ID_MUSICS_BY_GENRE+"/Songs";
        }

        // Unsubscribing before subscribing is required if this mediaId already has a subscriber
        // on this MediaBrowser instance. Subscribing to an already subscribed mediaId will replace
        // the callback, but won't trigger the initial callback.onChildrenLoaded.
        //
        // This is temporary: A bug is being fixed that will make subscribe
        // consistently call onChildrenLoaded initially, no matter if it is replacing an existing
        // subscriber or not. Currently this only happens if the mediaID has no previous
        // subscriber or if the media content changes on the service side, so we need to
        // unsubscribe first.
        mMediaFragmentListener.getMediaBrowser().unsubscribe(mMediaId);

        mMediaFragmentListener.getMediaBrowser().subscribe(mMediaId, mSubscriptionCallback);

        // Add MediaController callback so we can redraw the list when metadata changes:
        MediaControllerCompat controller = ((FragmentActivity) getActivity())
                .getSupportMediaController();
        if (controller != null) {
            controller.registerCallback(mMediaControllerCallback);
        }
    }
    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    super.onMetadataChanged(metadata);
                    if (metadata == null) {
                        return;
                    }
                    LogHelper.d(TAG, "Received metadata change to media ",
                            metadata.getDescription().getMediaId());
                    mBrowserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
                    super.onPlaybackStateChanged(state);
                    LogHelper.d(TAG, "Received state change: ", state);

                    mBrowserAdapter.notifyDataSetChanged();
                }
            };
    private final MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(@NonNull String parentId,
                                             @NonNull List<MediaBrowserCompat.MediaItem> children) {
                    try {
                        LogHelper.d(TAG, "fragment onChildrenLoaded, parentId=" + parentId +
                                "  count=" + children.size());

                        mBrowserAdapter.clear();
                        for (MediaBrowserCompat.MediaItem item : children) {
                            mBrowserAdapter.add(item);
                        }
                        mBrowserAdapter.notifyDataSetChanged();
                    } catch (Throwable t) {
                        LogHelper.e(TAG, "Error on childrenloaded", t);
                    }
                }

                @Override
                public void onError(@NonNull String id) {
                    LogHelper.e(TAG, "browse fragment subscription onError, id=" + id);
                    Toast.makeText(getActivity(), R.string.error_loading_media, Toast.LENGTH_LONG).show();

                }
            };
    private static class BrowseAdapter extends ArrayAdapter<MediaBrowserCompat.MediaItem> {

        public BrowseAdapter(Activity context) {
            super(context, R.layout.media_list_item, new ArrayList<MediaBrowserCompat.MediaItem>());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MediaBrowserCompat.MediaItem item = getItem(position);
            return MediaItemViewHolder.setupListView((Activity) getContext(), convertView, parent,
                    item);
        }
    }
}
