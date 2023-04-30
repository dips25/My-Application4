package com.example.myappication4.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.myappication4.Models.Content;
import com.example.myappication4.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import java.util.ArrayList;

public class VideoAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Content> contents;
    Context context;
    public ArrayList<ExoPlayer> exoPlayerArrayList = new ArrayList<>();
    public VideoViewHolder viewHolder;
    public RequestManager requestManager;
    ExoPlayer videoPlayer;

    public VideoAdapter(Context context, ArrayList<Content> contents) {

        this.context = context;
        //this.requestManager = requestManager;
        this.contents = contents;



    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_profile_video , parent , false);
        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Content content = contents.get(position);

        Toast.makeText(context, "Video", Toast.LENGTH_SHORT).show();

        ((VideoViewHolder)holder).onBind(content , position);

//        ((VideoViewHolder)holder).playerView.setPlayer(videoPlayer);
//
//        String mediaUrl = content.getName();
//        if (mediaUrl != null) {
//
//            MediaItem mediaItem = MediaItem.fromUri(mediaUrl);
//            videoPlayer.setMediaItem(mediaItem);
//            videoPlayer.prepare();
//            videoPlayer.setPlayWhenReady(true);

   //     }


    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



//        ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
//
//        TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);
//        trackSelector.setParameters(trackSelector.getParameters().buildUpon()
//                .setMaxVideoSizeSd()
//                .setPreferredTextLanguage("en")
//                .build());
//
//        holder.player = new ExoPlayer.Builder(context)
//                .setTrackSelector(trackSelector)
//                .build();
//
//        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(content.getName()));
//        holder.player.setVideoSurfaceView(holder.surfaceView);
//        holder.player.setMediaItem(mediaItem);
//
//        holder.player.prepare();
//
//
//        holder.relativeLayout.setVisibility(View.VISIBLE);
//
//
//        holder.progress_seekbar.setMax((int) holder.player.getDuration());
//
//        //holder.play.setImageResource(R.drawable.play);
//
//        exoPlayerArrayList.add(holder.player);
//        viewHolder = holder;





//        Animation rotateAnim = AnimationUtils.loadAnimation(context , R.anim.anim_clockwise);
//
//        holder.play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (holder.player.isPlaying()) {
//
//                    holder.player.pause();
//                    holder.play.startAnimation(rotateAnim);
//                    holder.play.setImageResource(R.drawable.play);
//                    // countDownTimer.cancel();
//
//                } else {
//
//                    holder.player.play();
//                    holder.play.startAnimation(rotateAnim);
//                    holder.play.setImageResource(R.drawable.pause);
//                    // countDownTimer.start();
//                }
//            }
//        });
//
//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                holder.relativeLayout.setVisibility(View.INVISIBLE);
//
//            }
//        };
//
//        holder.relativeLayout.postDelayed(runnable , 5000 );
//
//        holder.progress_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                if (fromUser) {
//
//
//                    holder.player.seekTo(progress);
//
//                }
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        holder.surfaceView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                holder.relativeLayout.setVisibility(View.VISIBLE);
//                holder.relativeLayout.postDelayed(runnable , 5000);
//
//            }
//        });
//
//
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                holder.relativeLayout.removeCallbacks(runnable);
//                holder.relativeLayout.setVisibility(View.INVISIBLE);
//
//            }
//        });

    //}

    @Override
    public int getItemCount() {
        return contents.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        SurfaceView surfaceView;
//        ExoPlayer player;
//        SeekBar progress_seekbar;
//        RelativeLayout relativeLayout;
//        ImageView play;
//        ImageView volume;
//
//        ViewHolder(View view) {
//            super(view);
//
//            surfaceView = (SurfaceView) view.findViewById(R.id.videoview);
//            relativeLayout = (RelativeLayout) view.findViewById(R.id.controls_holder);
//            progress_seekbar = (SeekBar) view.findViewById(R.id.video_progress);
//            play = (ImageView) relativeLayout.findViewById(R.id.play);
//
//
//        }
//    }
}
