package com.example.myappication4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappication4.Models.Content;
import com.example.myappication4.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

public class ProfileVideoAdapter extends RecyclerView.Adapter<ProfileVideoAdapter.ViewHolder> {

    Context context;
    ArrayList<Content> contentArrayList;
    SimpleExoPlayer exoPlayer;

    public ProfileVideoAdapter(Context context , ArrayList<Content> contentArrayList) {

        this.context = context;
        this.contentArrayList = contentArrayList;

//        releasePlayer();
//
//        ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
//
//        TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);
//        trackSelector.setParameters(trackSelector.getParameters().buildUpon()
//                .setMaxVideoSizeSd()
//                .setPreferredTextLanguage("en")
//                .build());
//
//        exoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_profile_video , parent , false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Content content = contentArrayList.get(position);

        ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();

        TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);
        trackSelector.setParameters(trackSelector.getParameters().buildUpon()
                .setMaxVideoSizeSd()
                .setPreferredTextLanguage("en")
                .build());

        exoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();

        MediaItem mediaItem = MediaItem.fromUri(content.getName());

        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();

        holder.playerView.setPlayer(exoPlayer);
        holder.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        holder.playerView.setUseController(false);

    }

    @Override
    public int getItemCount() {
        return contentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        PlayerView playerView;

        public ViewHolder(View view) {
            super(view);

            playerView = (PlayerView) view.findViewById(R.id.single_profileVideo);
        }
    }

    public void releasePlayer() {

        if (exoPlayer != null) {

            exoPlayer.release();
            exoPlayer = null;

        }


    }
}
