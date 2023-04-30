package com.example.myappication4;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.RequestManager;
import com.example.myappication4.Models.Content;
import com.example.myappication4.R;
import com.example.myappication4.adapters.VideoViewHolder;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerRecycler extends RecyclerView {

    private enum VolumeState {OFF , ON};
    Context context;
    PlayerView videoSurfaceView;
    public SimpleExoPlayer videoPlayer;
    ImageView play , volume;

    ProgressBar progressBar;
    SeekBar seekBar;
    RelativeLayout relativeLayout;
    FrameLayout frameLayout;
    View viewHolderParent;
    RequestManager requestManager;

    int screenDefaultHeight;
    int videoSurfaceDefaultHeight;
    int startPosition;
    int endPosition;
    int seekProgress;

    int playPosition = -1;
    boolean isVideoAdded;

    ArrayList<Content> contentArrayList = new ArrayList<>();
    VolumeState volumeState;

    int progress = 0;
    public MyCount myCount;
    long REM_TIME;

    long VIDEO_MAX_TIME;
    int prevposition = -1;
    Runnable runnable;
    boolean hasPlayed;
    boolean isBuffered;


    public VideoPlayerRecycler(@NonNull Context context) {
        super(context);
        //this.context = context;
        init(context);
    }

    public VideoPlayerRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //this.context = context;
        init(context);
    }

    private void init(Context context){
        this.context = context.getApplicationContext();
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;
        //videoSurfaceView = new PlayerView(this.context);
        //videoSurfaceView.setId(View.generateViewId());
        relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , Utils.getPixelsFromDp(context , 400)));
        relativeLayout.setAlpha(0.6f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(48 , 48);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout.setId(View.generateViewId());
        play = new ImageView(context);
        play.setImageResource(R.drawable.pause);
        seekBar = new SeekBar(context);
        seekBar.setId(View.generateViewId());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(5 , 5 , 5 , 10);
        relativeLayout.addView(seekBar , params);
        relativeLayout.addView(play , layoutParams);
        //videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);


        //videoSurfaceView.setUseController(false);
        //videoSurfaceView.setPlayer(videoPlayer);
        //setVolumeControl(VolumeState.ON);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {



                    if(!recyclerView.canScrollVertically(1)){
                        playVideo(true);
                    }
                    else{
                        playVideo(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (viewHolderParent != null && viewHolderParent.equals(view)) {
                    //resetVideoView();
                }

            }
        });

    }

    public void playVideo(boolean isEndOfList) {

        int targetPosition;

        if(!isEndOfList){
            int startPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

            Log.d(VideoPlayerRecycler.class.getSimpleName(), "StartPOsition: " + startPosition);
            Log.d(VideoPlayerRecycler.class.getSimpleName(), "EndPOsition: " + endPosition);


            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }

            Log.d(VideoPlayerRecycler.class.getSimpleName(), "FinalEnd: " + endPosition);


            if (startPosition < 0 || endPosition < 0) {
                return;
            }


            if (startPosition != endPosition) {
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);//1274
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);//674

                targetPosition = startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            }
            else {
                targetPosition = startPosition;
            }
        }
        else{
            targetPosition = contentArrayList.size() - 1;
        }





        Log.d(VideoPlayerRecycler.class.getSimpleName(), "TargetPosition: " + targetPosition);
        Log.d(VideoPlayerRecycler.class.getSimpleName(), "VisiblePosition:"  + ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition());


        int currentPosition = targetPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

        if (prevposition==-1 && currentPosition==0) {

            hasPlayed = false;

            prevposition = currentPosition;

            View child = getChildAt(currentPosition);
            if (child == null) {
                return;
            }


            VideoViewHolder holder = (VideoViewHolder) child   .getTag();
            if (holder == null) {
                playPosition = -1;
                return;
            }

            //progressBar = holder.progressBar;
            //requestManager = holder.requestManager;
            videoSurfaceView = holder.playerView;
            frameLayout = holder.itemView.findViewById(R.id.root_layout);
            videoSurfaceView.setUseController(false);

            frameLayout.addView(relativeLayout);

            releasePlayer();
            if (videoPlayer==null) {

                ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();

                TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);
                trackSelector.setParameters(trackSelector.getParameters().buildUpon()
                        .setMaxVideoSizeSd()
                        .setPreferredTextLanguage("en")
                        .build());

                videoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();

            }
            videoSurfaceView.setPlayer(videoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, "RecyclerView VideoPlayer"));
            String mediaUrl = contentArrayList.get(targetPosition).getName();
            if (mediaUrl != null) {

                MediaItem mediaItem = MediaItem.fromUri(mediaUrl);
                videoPlayer.setMediaItem(mediaItem);
                videoPlayer.prepare();
                videoPlayer.setPlayWhenReady(true);
                videoPlayer.play();


            }

//            videoPlayer.addListener(new Player.Listener() {
//
//                @Override
//                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                    switch (playbackState) {
//
//                        case ExoPlayer.STATE_BUFFERING:
//
//                            Toast.makeText(context, "Buffered", Toast.LENGTH_SHORT).show();
//
//                            progressBar.setVisibility(VISIBLE);
//                            isBuffered = true;
//
//                            if (myCount != null) {
//
//                                myCount.cancel();
//                                myCount = null;
//
//                            }
//
//
//                            break;
//
//
//
//                        case Player.STATE_READY:
//
//                            progressBar.setVisibility(GONE);
//
//                            if (isBuffered) {
//
//                                isBuffered = false;
//
//                                if (REM_TIME != 0) {
//
//                                    seekBar.setMax((int) REM_TIME/1000);
//                                    myCount = new MyCount(REM_TIME , 1000);
//                                    myCount.start();
//                                    break;
//
//
//                                }
//
//
//                            }
//
//                            VIDEO_MAX_TIME = videoPlayer.getDuration();
//                            seekBar.setMax((int) VIDEO_MAX_TIME/1000);
//                            myCount = new MyCount(VIDEO_MAX_TIME , 1000);
//                            myCount.start();
//
//                            break;
//
//                        case Player.STATE_ENDED:
//
//                            VIDEO_MAX_TIME = videoPlayer.getDuration();
//                            seekBar.setMax((int) VIDEO_MAX_TIME/1000);
//                            seekBar.setProgress(0);
//
//                            break;
//
//                    }
//
//
//
//                }
//            });


            setOnClickListeners();

            return;

        } else if (currentPosition != prevposition) {

            prevposition = currentPosition;

            View child = getChildAt(currentPosition);
            if (child == null) {
                return;
            }

            VideoViewHolder holder = (VideoViewHolder) child.getTag();
            if (holder == null) {
                playPosition = -1;
                return;
            }

            releasePlayer();

            //progressBar = holder.progressBar;
            //requestManager = holder.requestManager;
            videoSurfaceView = holder.playerView;
            frameLayout = holder.itemView.findViewById(R.id.root_layout);
            videoSurfaceView.setUseController(false);

            ViewGroup group = (ViewGroup) relativeLayout.getParent();

            if (group != null) {

                group.removeView(relativeLayout);
            }

            frameLayout.addView(relativeLayout);

            play.setImageResource(R.drawable.pause);

            if (videoPlayer==null) {

                ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();

                TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);
                trackSelector.setParameters(trackSelector.getParameters().buildUpon()
                        .setMaxVideoSizeSd()
                        .setPreferredTextLanguage("en")
                        .build());

                videoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();

            }
            videoSurfaceView.setPlayer(videoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, "RecyclerView VideoPlayer"));
            String mediaUrl = contentArrayList.get(targetPosition).getName();
            if (mediaUrl != null) {

                MediaItem mediaItem = MediaItem.fromUri(mediaUrl);
                videoPlayer.setMediaItem(mediaItem);
                videoPlayer.prepare();
                videoPlayer.setPlayWhenReady(true);

            }

//            videoPlayer.addListener(new Player.Listener() {
//
//                @Override
//                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                    switch (playbackState) {
//
//                        case ExoPlayer.STATE_BUFFERING:
//
//                            Toast.makeText(context, "Buffered", Toast.LENGTH_SHORT).show();
//
//                            progressBar.setVisibility(VISIBLE);
//                            isBuffered = true;
//
//                            if (myCount != null) {
//
//                                myCount.cancel();
//                                myCount = null;
//
//                            }
//
//
//                            break;
//
//
//
//                        case Player.STATE_READY:
//
//                            progressBar.setVisibility(GONE);
//
//                            if (isBuffered) {
//
//                                isBuffered = false;
//
//                                if (REM_TIME != 0) {
//
//                                    seekBar.setMax((int) REM_TIME/1000);
//                                    myCount = new MyCount(REM_TIME , 1000);
//                                    myCount.start();
//                                    break;
//
//
//                                }
//
//
//                            }
//
//                            VIDEO_MAX_TIME = videoPlayer.getDuration();
//                            seekBar.setMax((int) VIDEO_MAX_TIME/1000);
//                            myCount = new MyCount(VIDEO_MAX_TIME , 1000);
//                            myCount.start();
//
//                            break;
//
//                        case Player.STATE_ENDED:
//
//                            VIDEO_MAX_TIME = videoPlayer.getDuration();
//                            seekBar.setMax((int) VIDEO_MAX_TIME/1000);
//                            seekBar.setProgress(0);
//
//                            break;
//
//                    }
//
//
//
//                }
//            });

            setOnClickListeners();

            return;

        } else {

            return;
        }

    }



    private OnClickListener videoViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleVolume();
        }
    };

    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     * @param playPosition
     * @return
     */
    private int getVisibleVideoSurfaceHeight(int playPosition) {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();


        View child = getChildAt(at);
        if (child == null) {
            return 0;
        }

        int[] location = new int[2];
        child.getLocationInWindow(location);

        Log.d(VideoPlayerRecycler.class.getSimpleName(), "getVisibleVideoSurfaceHeight: " + videoSurfaceDefaultHeight);
        Log.d(VideoPlayerRecycler.class.getSimpleName(), "getDefaultScreenHeight: " + screenDefaultHeight);

        Log.d(VideoPlayerRecycler.class.getSimpleName(), "LocationY: " + location[1]);

        if (location[1] < 0) {
            return location[1] + videoSurfaceDefaultHeight;
        } else {
            return screenDefaultHeight - location[1];
        }
    }


    // Remove the old player
    private void removeVideoView(PlayerView videoView) {
        ViewGroup parent = (ViewGroup) videoView.getParent();

        if (parent != null) {

            int index = parent.indexOfChild(videoView);
            int index1 = parent.indexOfChild(relativeLayout);
            if (index1 >= 0) {

                parent.removeViewAt(index1);
            }
            if (index >= 0) {
                parent.removeViewAt(index);
                isVideoAdded = false;
                viewHolderParent.setOnClickListener(null);
//            timer.cancel();
//            seekBar.setProgress(0);

            }
        }




    }

    private void addVideoView(){

        ViewGroup viewGroup =(ViewGroup) videoSurfaceView.getParent();
        ViewGroup viewGroup1 = (ViewGroup) relativeLayout.getParent();

        if (viewGroup != null) {

            viewGroup.removeAllViews();

        }

        if (viewGroup1 != null) {

            viewGroup1.removeAllViews();

        }

       // viewGroup1.removeAllViews();

       // frameLayout.removeAllViews();

//        ConstraintSet constraintSet = new ConstraintSet();
//        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 600);
//        videoSurfaceView.setLayoutParams(layoutParams);
//        frameLayout.addView(videoSurfaceView);
//        frameLayout.addView(relativeLayout);
//
//        constraintSet.connect(videoSurfaceView.getId() , ConstraintSet.START, ConstraintSet.PARENT_ID , ConstraintSet.START);
//        constraintSet.connect(videoSurfaceView.getId() , ConstraintSet.END, ConstraintSet.PARENT_ID , ConstraintSet.END);
//        constraintSet.connect(videoSurfaceView.getId() , ConstraintSet.TOP, ConstraintSet.PARENT_ID , ConstraintSet.TOP);
//        constraintSet.connect(videoSurfaceView.getId() , ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID , ConstraintSet.BOTTOM);
//
//        constraintSet.connect(relativeLayout.getId() , ConstraintSet.START, ConstraintSet.PARENT_ID , ConstraintSet.START);
//        constraintSet.connect(relativeLayout.getId() , ConstraintSet.END, ConstraintSet.PARENT_ID , ConstraintSet.END);
//        constraintSet.connect(relativeLayout.getId() , ConstraintSet.TOP, ConstraintSet.PARENT_ID , ConstraintSet.TOP);
//        constraintSet.connect(relativeLayout.getId() , ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID , ConstraintSet.BOTTOM);
//
//        constraintSet.applyTo(frameLayout);



        isVideoAdded = true;
        videoSurfaceView.requestFocus();
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setAlpha(1);

    }

    private void resetVideoView(){
        if(isVideoAdded){
            removeVideoView(videoSurfaceView);
            playPosition = -1;
            videoSurfaceView.setVisibility(INVISIBLE);

        }
    }

    public void releasePlayer() {

        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;

            if (myCount != null) {

                myCount.cancel();
                myCount = null;

            }

            myCount = null;
            isBuffered = false;
            seekBar.setProgress(0);
            progress = 0;

        }

       // viewHolderParent = null;
       // timer.cancel();
       // timer = null;
    }

    public void setVolume() {

        if (videoPlayer != null) {

            if (volumeState == VolumeState.ON) {

                videoPlayer.setVolume(1f);
                requestManager.load(R.drawable.volume_off).into(volume);
                volumeState = VolumeState.ON;

            } else {

                videoPlayer.setVolume(0f);
                requestManager.load(R.drawable.volume_up).into(volume);
                volumeState = VolumeState.ON;

            }
        }


    }

    private void toggleVolume() {

        if (videoPlayer != null) {

            if (volumeState == VolumeState.OFF) {

                videoPlayer.setVolume(1f);
                requestManager.load(R.drawable.volume_off).into(volume);
                volumeState = VolumeState.ON;

            } else {

                videoPlayer.setVolume(0f);
                requestManager.load(R.drawable.volume_up).into(volume);
                volumeState = VolumeState.OFF;

            }
        }

    }

    private void setVolumeControl(VolumeState state){
        volumeState = state;
        if(state == VolumeState.OFF){
            videoPlayer.setVolume(0f);
            animateVolumeControl();
        }
        else if(state == VolumeState.ON){
            videoPlayer.setVolume(1f);
            animateVolumeControl();
        }
    }

    private void animateVolumeControl(){
        if(volume != null){
            volume.bringToFront();
            if(volumeState == VolumeState.OFF){
                requestManager.load(R.drawable.volume_up)
                        .into(volume);
            }
            else if(volumeState == VolumeState.ON){
                requestManager.load(R.drawable.volume_off)
                        .into(volume);
            }
            volume.animate().cancel();

            volume.setAlpha(1f);

            volume.animate()
                    .alpha(0f)
                    .setDuration(600).setStartDelay(1000);
        }
    }

    public void setMediaObjects(ArrayList<Content> mediaObjects){
        this.contentArrayList = mediaObjects;
    }

    class MyCount extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);


        }


        @Override
        public void onTick(long millisUntilFinished) {


            REM_TIME = millisUntilFinished;
            progress = (int) (VIDEO_MAX_TIME-millisUntilFinished)/1000;
            Log.d("VideoTimer", "onTick: " + progress);
            seekBar.setProgress(progress);

        }


        @Override
        public void onFinish() {



            Toast.makeText(context, "Timer Cancelled.", Toast.LENGTH_SHORT).show();

            //seekBar.setProgress(0);
            //videoPlayer.seekTo(0);
            play.setImageResource(R.drawable.play);
            REM_TIME = 0;
            myCount = null;

        }



    }

    private void setOnClickListeners() {


        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videoPlayer.isPlaying()) {

                    Animation animation = AnimationUtils.loadAnimation(context , R.anim.anim_clockwise);

                    videoPlayer.pause();
                    play.startAnimation(animation);
                    play.setImageResource(R.drawable.play);
                    //myCount.cancel();
                    //myCount = null;
                    //seekBar.setProgress(progress);



                } else {

//                    if (REM_TIME == 0) {
//
//                        seekBar.setProgress(0);
//                        videoPlayer.seekTo(0);
//                        //myCount = new MyCount(VIDEO_MAX_TIME , 1000);
//                        //myCount.start();
//                        return;
//
//
//                    }

                    Animation animation = AnimationUtils.loadAnimation(context , R.anim.anim_clockwise);
                    videoPlayer.play();
                    play.startAnimation(animation);
                    play.setImageResource(R.drawable.pause);
                    seekBar.setProgress(progress);








                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {

                    Log.d(VideoPlayerRecycler.class.getSimpleName(), "onProgressChanged: " + progress);


//                    REM_TIME = VIDEO_MAX_TIME -(progress* 1000);
//                    //seekBar.setProgress(progress);
//                    videoPlayer.seekTo(progress*1000);


                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

//                if (myCount != null) {
//
////                    myCount.cancel();
//
//                }






            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                seekBar.setProgress((int) REM_TIME/1000);
                //myCount = new MyCount(REM_TIME , 1000);
                //myCount.start();






            }
        });



        if (runnable == null) {

            runnable = new Runnable() {
                @Override
                public void run() {

                    if (relativeLayout != null) {

                        relativeLayout.setVisibility(View.INVISIBLE);

                    }



                }
            };

        }


        videoSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (relativeLayout!= null) {

                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.postDelayed(runnable , 5000);

                }



            }
        });

        if (relativeLayout != null) {

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    relativeLayout.removeCallbacks(runnable);
                    relativeLayout.setVisibility(View.INVISIBLE);

                }
            });

        }
    }


}
