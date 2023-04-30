package com.example.myappication4.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.R;
import com.example.myappication4.VideoPlayerRecycler;
import com.example.myappication4.adapters.VideoAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.io.File;
import java.util.ArrayList;

public class VideoFragment2 extends Fragment {

    VideoPlayerRecycler videoRecycler;
    VideoAdapter videoAdapter;
    final ArrayList<Content> contentArrayList = new ArrayList<>();
    final ArrayList<Content> contents = new ArrayList<>();
    String[]paths = new String[]{"/sdcard/DCIM/Camera/VID_20220331_132350.mp4"};
    boolean isScrolling = false;
    int position = 0;
    int currentItem;
    int totalItems;
    int scrolledOutItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video2 , container , false);

        videoRecycler = (VideoPlayerRecycler) view.findViewById(R.id.videorecycler);
        videoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        videoRecycler.setHasFixedSize(true);

        //videoAdapter = new VideoAdapter(getContext() , contents , initGlide());

        videoRecycler.setAdapter(videoAdapter);

        videoAdapter.notifyDataSetChanged();

        return view;
    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    public ArrayList<Content> getAllPosts(String[] paths) {
        int i = 0;
        //ArrayList<Content> contents = new ArrayList<>();
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
        videoRecycler.setMediaObjects(contents);
        videoAdapter.notifyDataSetChanged();

        return contentArrayList;


    }

    public void getAllPosts(Content content) {

        contents.add(content);
        videoRecycler.setMediaObjects(contents);
        videoAdapter.notifyDataSetChanged();

//        int i = 0;
//
//        ArrayList<String> stringArrayList = new ArrayList<>();
//
//        File file = Environment.getExternalStorageDirectory();
//        File file1 = new File(file , "DCIM/Camera");
//
//
//        FirebaseFirestore.getInstance().collection("Posts")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                        if (error != null) {
//
//                            Toast.makeText(getActivity(), "Error:" + error, Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        if (!value.isEmpty()) {
//
//                            for (DocumentChange documentChange : value.getDocumentChanges()) {
//
//                                switch (documentChange.getType()) {
//
//                                    case ADDED:
//                                        PostImages postImages = documentChange.getDocument().toObject(PostImages.class);
//
//                                        for (Content content : postImages.getContents()) {
//
//                                            if (content.getType().equalsIgnoreCase("video"));
//
//                                            contents.add(content);
//
//                                        }
//
//                                        videoRecycler.setMediaObjects(contents);
//                                        videoAdapter.notifyDataSetChanged();
//
//                                        break;
//                                }
//                            }
//
//
//                        }
//
//
//
////                        for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
////
////                            PostImages postImages = documentSnapshot.toObject(PostImages.class);
////
////                            for (Content content : postImages.getContents()) {
////
////                                if (content.getType().equalsIgnoreCase("video")) {
////
////                                    Log.d("Contents", "onEvent: " + content);
////
////                                    contents.add(content);
////
////                                    videoRecycler.setMediaObjects(contents);
////                                    videoAdapter.notifyDataSetChanged();
////
////
////
////                                }
////                            }
////
////
////                        }
//
//
//
//                    }
//                });



    }

//    class FetchTask extends AsyncTask<String , Integer , String> {
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            FirebaseFirestore.getInstance().collection("Posts")
//                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                            if (error != null) {
//
//                                Toast.makeText(getActivity(), "Error:" + error, Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
////                        if (!value.isEmpty()) {
////
////                            for (DocumentChange documentChange : value.getDocumentChanges()) {
////
////                                switch (documentChange.getType()) {
////
////                                    case ADDED:
////                                        PostImages postImages = documentChange.getDocument().toObject(PostImages.class);
////
////                                        for (Content content : postImages.getContents()) {
////
////                                            if (content.getType().equalsIgnoreCase("video"));
////
////                                            contents.add(content);
////                                        }
////
////                                        break;
////                                }
////                            }
////
////                            contentArrayList.addAll(contents);
////                            return;
////                        }
//
//
//
//                            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
//
//                                PostImages postImages = documentSnapshot.toObject(PostImages.class);
//
//                                for (Content content : postImages.getContents()) {
//
//                                    if (content.getType().equalsIgnoreCase("video")) {
//
//                                        Log.d("Contents", "onEvent: " + content);
//
//                                        contents.add(content);
//
//
//
//                                    }
//                                }
//
//
//                            }
//
//
//
//                        }
//                    });
//            return "Done";
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//
//
//        }
//    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        if (videoRecycler != null) {

            //videoRecycler.releasePlayer();
        }
    }
}
