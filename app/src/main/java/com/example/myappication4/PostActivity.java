package com.example.myappication4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.bumptech.glide.Glide;
import com.example.myappication4.Models.DirImages;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.activities.PostPreviewActivity;
import com.example.myappication4.adapters.GridAdapter;
import com.example.myappication4.adapters.ViewPagerAdapter;
import com.example.myappication4.fragments.MyImagesFragment;
import com.example.myappication4.fragments.MyVideoFragment;
import com.example.myappication4.services.PostService;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostActivity extends AppCompatActivity implements ImageVideo {

    private static final String TAG = PostActivity.class.getSimpleName();
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<DirImages> dirImages = new ArrayList<>();
    GridView gridView;
    GridAdapter gridAdapter;
    ArrayList<PostImages> postImages = new ArrayList<>();
    ImageView post_image;
    VideoView post_video;
    ImageView camera;
    Uri finalUri;
    ImageVideo imageVideo;
    ImageView cancel;
    TextView post;
    File finalFile;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public static int count=0;
    public static final int MAX_COUNT=6;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageVideo = this;

        camera = (ImageView) findViewById(R.id.camera);
        cancel = (ImageView) findViewById(R.id.cancel);
        post = (TextView) findViewById(R.id.post);
        post.setText("Next(0)");

        post_image = (ImageView) findViewById(R.id.post_image);
        post_video = (VideoView) findViewById(R.id.post_video);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.image_video_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(new MyImagesFragment() , "Images");
        viewPagerAdapter.addFragments(new MyVideoFragment() , "Videos");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalFile!=null) {

                        finalFile.delete();
                        int index = stringArrayList.indexOf(finalUri.toString());
                        stringArrayList.remove(finalUri.toString());
                        finalUri = null;
                        finalFile = null;


                        if (!stringArrayList.isEmpty()) {

                            if (index == stringArrayList.size()) {

                                Glide.with(PostActivity.this).load(stringArrayList.get(index-1)).into(post_image);

                            } else if (index == 0 && !stringArrayList.get(0).isEmpty()) {

                                Glide.with(PostActivity.this).load(stringArrayList.get(0)).into(post_image);

                            } else if (index > 0) {

                                Glide.with(PostActivity.this).load(stringArrayList.get(index)).into(post_image);

                            } else {

                                Glide.with(PostActivity.this).load(getResources().getDrawable(R.drawable.user)).into(post_image);


                            }


                        } else {

                            Glide.with(PostActivity.this).load(getResources().getDrawable(R.drawable.user)).into(post_image);

                        }

                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {

                    File cameraFile = createImageFile();

                    finalUri = FileProvider.getUriForFile(PostActivity.this , "com.example.myappication4.fileprovider" , cameraFile);

                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT , finalUri);
                startActivityForResult(intent , 100);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PostActivity.this , PostPreviewActivity.class);
                intent.putExtra("arraylist" , stringArrayList);
                startActivity(intent);


            }
        });

    }

    private File createImageFile() {

        File file = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file1 = new File(file , "Camera");

        if (!file1.exists()) {

            file1.mkdir();
        }

        String imagename = new SimpleDateFormat("ddmmyy_hhmmss").format(new Date()) + UUID.randomUUID().toString() + ".jpg";
        finalFile = new File(file1 , imagename);
        return finalFile;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            imageVideo.showImage(finalUri.toString());
            stringArrayList.add(finalUri.toString());
            Log.d(TAG, "onActivityResult: " + finalUri.toString());
        }
    }



    @Override
    public void showImage(String image) {

        if (image.contains("Camera")) {

            cancel.setVisibility(View.VISIBLE);

        } else {

            cancel.setVisibility(View.GONE);
        }

        post_video.setVisibility(View.GONE);
        post_image.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(image)
                .into(post_image);


    }

    @Override
    public void showVideo(String video) {
        post_image.setVisibility(View.GONE);
        post_video.setVisibility(View.VISIBLE);
        post_video.setVideoURI(Uri.parse(video));
        post_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

    }

    @Override
    public void getImage(String image) {

        stringArrayList.add(image);
        post.setText("Next(" + count +")");
    }

    @Override
    public void getVideo(String video) {

            stringArrayList.add(video);
            post.setText("Next("+count+")");



    }

    @Override
    public void removeImage(String image) {

        if (stringArrayList.contains(image)) {

            stringArrayList.remove(image);
            post.setText("Next("+count+")");
        }

    }

    @Override
    public void removeVideo(String video) {

        if (stringArrayList.contains(video)) {

            stringArrayList.remove(video);
            post.setText("Next("+count+")");
        }

    }


}