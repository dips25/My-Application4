package com.example.myappication4.adapters;

import android.media.Image;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.myappication4.Models.Content;
import com.example.myappication4.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public PlayerView playerView;
    public TextView name;
    public TextView description , date , time;
    public CircleImageView profileImage;
    public View parentView;
    public FrameLayout rootView;
    public SeekBar progress_seekbar;
    public RelativeLayout relativeLayout;
    public ImageView play;
    public ProgressBar progressBar;
    public ImageView volume;
    //public RequestManager requestManager;


    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);


        parentView = itemView;
        playerView = (PlayerView) itemView.findViewById(R.id.single_profileVideo);
        //name = (TextView) itemView.findViewById(R.id.name);
        //description = (TextView) itemView.findViewById(R.id.post_description);
        //date = (TextView) itemView.findViewById(R.id.date);
        //time = (TextView) itemView.findViewById(R.id.time);
        rootView = itemView.findViewById(R.id.root_layout);

        //progressBar = itemView.findViewById(R.id.buffer_progress);




    }

    public void onBind(Content content, int position) {

        //this.requestManager = requestManager;




        parentView.setTag(this);

    }
}
