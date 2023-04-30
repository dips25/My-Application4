package com.example.myappication4;

import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myappication4.Models.Content;
import com.example.myappication4.adapters.MultiPagerAdapter;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class MultiViewPager extends ViewPager {

    int defaultHeight;
    PlayerView playerView;
    ImageView imageView;
    public FrameLayout rootLayout;
    SimpleExoPlayer exoPlayer;
    Context context;
    ArrayList<Content> contents = new ArrayList<>();
    int childCount = 0;
    public MultiViewPager(@NonNull Context context) {
        super(context);
        init(context);
        this.context = context;
    }

    public MultiViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.context = context;
    }

    public void init(Context context){

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        defaultHeight = point.x;

        playerView = new PlayerView(context);
        playerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , defaultHeight));

        imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , defaultHeight));

        rootLayout = new FrameLayout(context);
        rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , defaultHeight));
        //rootLayout.addView(playerView);



        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //removeOldView(rootLayout);
                //releasePlayer();
            }

            @Override
            public void onPageSelected(int position) {

                //addNewView(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                Log.d(MultiViewPager.class.getSimpleName(), "onPageScrollStateChanged: " + state);



            }
        });


    }

    public void removeOldView(FrameLayout rootLayout) {

        int viewGroup = ((ViewGroup) rootLayout).getChildCount();

        if (viewGroup > 0) {

            rootLayout.removeViewAt(0);
        }

    }

    public View addNewView(int position) {

//        FrameLayout frameLayout = new FrameLayout(context);
//        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , defaultHeight));


        if (contents.get(position).getType().equalsIgnoreCase("video")) {

            releasePlayer();

            PlayerView playerView = new PlayerView(context);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
            playerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
//

            ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();

            TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);
            trackSelector.setParameters(trackSelector.getParameters().buildUpon()
                    .setMaxVideoSizeSd()
                    .setPreferredTextLanguage("en")
                    .build());

            exoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();

            MediaItem mediaItem = MediaItem.fromUri(contents.get(position).getName());


            exoPlayer.setMediaItem(mediaItem);

//            if (playerView.getParent()!=null) {
//
//                //((ViewGroup)playerView.getParent()).removeAllViews();
//
//            }
//
//            try {
//
//                frameLayout.addView(playerView);
//
//            } catch (IllegalStateException e) {
//
//                ((ViewGroup)playerView.getParent()).removeViewAt(0);
//                frameLayout.addView(playerView);
//
//
//            }

            //frameLayout.addView(playerView);



//            if (rootLayout.getParent() == null) {
//
//                rootLayout.addView(playerView);
//
//            } else {
//
//                rootLayout.removeViewAt(0);
//                rootLayout.addView(playerView);
//
//            }

            //rootLayout.addView(playerView , childCount);
            //childCount++
            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare();

            //exoPlayer.setPlayWhenReady(true);

            return playerView;

        } else {

            if (imageView.getParent()!=null) {

                //((ViewGroup)imageView.getParent()).removeAllViews();

            }

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

            //rootLayout.addView(imageView , childCount);
            //childCount++;

            //rootLayout.removeAllViews();
            Glide.with(this).load(contents.get(position).getName()).placeholder(R.drawable.placeholder).into(imageView);
            return imageView;

//            try {
//
//                frameLayout.addView(imageView);
//
//            } catch (IllegalStateException e) {
//
//                ((ViewGroup)imageView.getParent()).removeViewAt(0);
//                frameLayout.addView(imageView);
//
//
//            }
//
            //return imageView;
            //Log.d("AddView", "addNewView: " + contents.get(position).getName());


        }

        //return rootLayout;


    }

    public void releasePlayer() {

        if (exoPlayer != null) {

            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public void setMediaObjects(ArrayList<Content> contents) {

        this.contents = contents;
    }
}
