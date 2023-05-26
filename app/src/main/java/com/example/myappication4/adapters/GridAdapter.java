package com.example.myappication4.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myappication4.ImageVideo;
import com.example.myappication4.Models.DirImages;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.R;

import java.io.File;
import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<DirImages> dirImages = new ArrayList<>();
    ArrayList<String> selectedImages = new ArrayList<>();
    LayoutInflater inflater;
    ImageVideo imageVideo;
    volatile boolean isMultiSelectionEnabled = true;


    public GridAdapter(Context context , ArrayList<DirImages> dirImages) {

        this.context = context;
        this.dirImages = dirImages;
        this.imageVideo = (ImageVideo) context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return dirImages.size();
    }

    @Override
    public Object getItem(int position) {
        return dirImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        final DirImages dirImages = this.dirImages.get(position);


            if (dirImages.getName().endsWith(".jpg") || dirImages.getName().endsWith(".png") || dirImages.getName().endsWith(".jpeg")) {

                convertView = inflater.inflate(R.layout.single_item_post , parent , false);
                Bitmap bitmap = decodeFile(new File(dirImages.getName()));

                ImageView single_post_image = (ImageView) convertView.findViewById(R.id.single_post_image);
                single_post_image.setImageDrawable(null);
                CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.single_item_check);

                //Glide.with(context).load(dirImages.getName()).into(single_post_image);
                single_post_image.setImageBitmap(null);

                single_post_image.setImageBitmap(bitmap);

                if (isMultiSelectionEnabled) {

                    checkBox.setVisibility(View.VISIBLE);

                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            imageVideo.getImage(dirImages.getName());

                        } else {

                            imageVideo.removeImage(dirImages.getName());


                        }

                    }
                });

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageVideo.showImage(dirImages.getName());
                    }
                });



            }    else {

                convertView = inflater.inflate(R.layout.single_item_video , parent , false);

                VideoView videoView = (VideoView) convertView.findViewById(R.id.single_post_video);

                videoView.setVideoURI(Uri.parse(dirImages.getName()));


                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        mp.seekTo(1000);

                        mp.stop();
                    }
                });

                CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.single_item_check);

                if (isMultiSelectionEnabled) {

                    checkBox.setVisibility(View.VISIBLE);

                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageVideo.showVideo(dirImages.getName());
                    }
                });

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            imageVideo.getVideo(dirImages.getName());

                        } else {

                            imageVideo.removeVideo(dirImages.getName());


                        }

                    }
                });



        }



        return convertView;
    }

    public Bitmap decodeFile(File file) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath() , options);

        options.inSampleSize = calculateInSampleSize(options , 512 , 90);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(file.getAbsolutePath() , options);



    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
