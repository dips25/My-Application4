package com.example.myappication4.activities;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myappication4.Models.Content;
import com.example.myappication4.R;
import com.example.myappication4.VideoPlayerRecycler;
import com.example.myappication4.adapters.VideoAdapter;
import com.example.myappication4.fragments.PostsFragment;

import java.io.File;
import java.util.ArrayList;

public class SampleActvity extends AppCompatActivity {

    VideoPlayerRecycler videoRecycler;
    VideoAdapter videoAdapter;
    ArrayList<Content> contentArrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        getAllPosts(new String[]{});

        videoRecycler = (VideoPlayerRecycler) findViewById(R.id.vidrecycler);
        videoRecycler.setLayoutManager(new LinearLayoutManager(SampleActvity.this));
        videoRecycler.setMediaObjects(contentArrayList);

        videoAdapter = new VideoAdapter(SampleActvity.this , contentArrayList);
        videoRecycler.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();


    }

    public ArrayList<Content> getAllPosts(String[] paths) {
        int i = 0;
        ArrayList<Content> contents = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        File file = Environment.getExternalStorageDirectory();
        File file1 = new File(file , "DCIM/Camera");



        for (File s : file1.listFiles()) {

            if (s.getAbsolutePath().endsWith(".mp4")) {

                i+=1;

                if (i==12){

                    break;
                }

                contents.add(new Content("video" , s.getAbsolutePath()));
                stringArrayList.add(s.getAbsolutePath());

            }

        }

        Log.d(PostsFragment.class.getSimpleName(), "getAllPosts: " + stringArrayList.toString());
        contentArrayList.addAll(contents);
//        videoRecycler.setMediaObjects(contents);
//        videoAdapter.notifyDataSetChanged();



        return contentArrayList;


    }
}
