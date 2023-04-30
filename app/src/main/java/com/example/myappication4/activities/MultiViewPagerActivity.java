package com.example.myappication4.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.MultiViewPager;
import com.example.myappication4.R;
import com.example.myappication4.adapters.MultiPagerAdapter;
import com.example.myappication4.fragments.PostsFragment;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class MultiViewPagerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MultiPagerAdapter multiPagerAdapter;
    String[]paths = new String[]{"/sdcard/DCIM/Camera/VID_20220331_132350.mp4"};
    ArrayList<PostImages> postImagesArrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiviewpager);

        recyclerView = (RecyclerView) findViewById(R.id.multiViewpager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        multiPagerAdapter = new MultiPagerAdapter(postImagesArrayList , MultiViewPagerActivity.this);
        recyclerView.setAdapter(multiPagerAdapter);

        getAllPosts(paths);


    }

    public void getAllPosts(String[] paths) {
        int i = 0;
        ArrayList<Content> contents = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        File file = Environment.getExternalStorageDirectory();
        File file1 = new File(file , "DCIM/Camera");

        for (File s : file1.listFiles()) {

            if (s.getAbsolutePath().endsWith(".jpg")) {

                i += 1;

                if (i == 4) {

                    break;
                }

                contents.add(new Content("image", s.getAbsolutePath()));
                stringArrayList.add(s.getAbsolutePath());

            }
        }

        i = 0;



        for (File s : file1.listFiles()) {

            if (s.getAbsolutePath().endsWith(".mp4")) {

                i+=1;

                if (i==4){

                    break;
                }

                contents.add(new Content("video" , s.getAbsolutePath()));
                stringArrayList.add(s.getAbsolutePath());

            }

        }

        Log.d(PostsFragment.class.getSimpleName(), "getAllPosts: " + stringArrayList.toString());

        PostImages postImages = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
        PostImages postImages1 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);

        PostImages postImages3 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
        PostImages postImages4 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);

        PostImages postImages5 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
        PostImages postImages6 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);

//        PostImages postImages2 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//        PostImages postImages3 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//
//        PostImages postImages4 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//        PostImages postImages5 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//
//        PostImages postImages6 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//        PostImages postImages7 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//
//        PostImages postImages8 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//        PostImages postImages9 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
        postImagesArrayList.add(postImages);
        postImagesArrayList.add(postImages1);
        postImagesArrayList.add(postImages3);
        postImagesArrayList.add(postImages4);
        postImagesArrayList.add(postImages5);
        postImagesArrayList.add(postImages6);

//        postImagesArrayList.add(postImages2);
//        postImagesArrayList.add(postImages3);
//
//        postImagesArrayList.add(postImages4);
//        postImagesArrayList.add(postImages5);
//
//        postImagesArrayList.add(postImages6);
//        postImagesArrayList.add(postImages7);
//
//        postImagesArrayList.add(postImages8);
//        postImagesArrayList.add(postImages9);

        multiPagerAdapter.notifyDataSetChanged();
    }

    public void getAllPosts() {

//        db.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                if (error != null) {
//
//                    return;
//                }
//
//                ArrayList<PostImages> postImages =(ArrayList<PostImages>) value.toObjects(PostImages.class);
//
//                MultiPagerAdapter multiPagerAdapter = new MultiPagerAdapter(postImages , MultiViewPagerActivity.this);
//                recyclerView.setAdapter(multiPagerAdapter);
//                multiPagerAdapter.notifyDataSetChanged();
//
//
//
//            }
//        });
    }
}
