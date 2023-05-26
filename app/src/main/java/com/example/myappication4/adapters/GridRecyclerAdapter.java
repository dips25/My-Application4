package com.example.myappication4.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myappication4.ImageVideo;
import com.example.myappication4.Models.DirImages;
import com.example.myappication4.PostActivity;
import com.example.myappication4.R;

import java.util.ArrayList;

public class GridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<DirImages> dirImagesArrayList;
    LayoutInflater inflater;
    ImageVideo imageVideo;
    public boolean isMultiSelectionEnabled = true;
    private static final int IMAGE_VIEW = 1;
    private static final int VIDEO_VIEW = 2;

    public GridRecyclerAdapter(Context context , ArrayList<DirImages> dirImagesArrayList) {

        this.context = context;
        this.dirImagesArrayList = dirImagesArrayList;
        this.inflater = LayoutInflater.from(context);
        imageVideo = (ImageVideo) context;


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == IMAGE_VIEW) {

            View view = inflater.inflate(R.layout.single_item_post , parent , false);
            return new ViewHolder(view);


        } else {

            View view = inflater.inflate(R.layout.single_item_video , parent , false);
            return new VideoViewHolder(view);


        }





    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DirImages dirImages = dirImagesArrayList.get(position);

        if (holder instanceof ViewHolder) {

            Glide.with(context).load(dirImages.getName()).placeholder(R.drawable.placeholder).into(((ViewHolder) holder).single_post_image);

            if (isMultiSelectionEnabled) {

                ((ViewHolder)holder).checkBox.setVisibility(View.VISIBLE);

            }

            ((ViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isChecked = ((CheckBox)v).isChecked();

                    if (!isChecked) {

                        PostActivity.count-=1;

                            ((CheckBox)v).setChecked(false);
                            imageVideo.removeImage(dirImages.getName());


                    } else {


                        if (PostActivity.count<PostActivity.MAX_COUNT) {
                            PostActivity.count+=1;
                            ((CheckBox)v).setChecked(true);
                            imageVideo.getImage(dirImages.getName());

                        } else {

                            ((CheckBox)v).setChecked(false);
                            Toast.makeText(context, "Cant add more contents.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });



            ((ViewHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imageVideo.showImage(dirImages.getName());
                }
            });



        } else if (holder instanceof VideoViewHolder) {

            ((VideoViewHolder) holder).videoView.setVideoURI(Uri.parse(dirImages.getName()));


            ((VideoViewHolder) holder).videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.seekTo(1000);

                    mp.stop();
                }
            });



            if (isMultiSelectionEnabled) {

                ((VideoViewHolder) holder).checkBox.setVisibility(View.VISIBLE);

            }

            ((VideoViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imageVideo.showVideo(dirImages.getName());
                }
            });

           ((VideoViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   boolean isChecked = ((CheckBox)v).isChecked();

                   if (!isChecked) {

                       PostActivity.count-=1;

                       ((CheckBox)v).setChecked(false);
                       imageVideo.removeVideo(dirImages.getName());


                   } else {


                       if (PostActivity.count<PostActivity.MAX_COUNT) {
                           PostActivity.count+=1;
                           ((CheckBox)v).setChecked(true);
                           imageVideo.getVideo(dirImages.getName());

                       } else {

                           ((CheckBox)v).setChecked(false);
                           Toast.makeText(context, "Cant add more contents.", Toast.LENGTH_SHORT).show();
                       }
                   }
               }
           });


        }

    }

    @Override
    public int getItemCount() {
        return dirImagesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView single_post_image;
        CheckBox checkBox;
        View view;

        public ViewHolder(View view) {

            super(view);
            this.view = view;

            checkBox = (CheckBox) view.findViewById(R.id.single_item_check);
            single_post_image = (ImageView) view.findViewById(R.id.single_post_image);

        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        VideoView videoView;
        CheckBox checkBox;
        View view;

        public VideoViewHolder(View view) {

            super(view);

            videoView = (VideoView) view.findViewById(R.id.single_post_video);
            checkBox = (CheckBox) view.findViewById(R.id.single_item_check);
            this.view = view;

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (dirImagesArrayList.get(position).getName().endsWith("jpg")
                || dirImagesArrayList.get(position).getName().endsWith("png")
        || dirImagesArrayList.get(position).getName().endsWith("jpeg")) {

            return IMAGE_VIEW;
        } else {

            return VIDEO_VIEW;
        }
    }
}
