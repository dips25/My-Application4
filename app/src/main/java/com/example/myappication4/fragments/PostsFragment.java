package com.example.myappication4.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myappication4.FetchData;
import com.example.myappication4.MainActivity;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.OnScrollListener;
import com.example.myappication4.R;
import com.example.myappication4.adapters.MultiPagerAdapter;
import com.example.myappication4.adapters.PostsAdapter;
import com.example.myappication4.adapters.PostsAdapter2;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class PostsFragment extends Fragment implements OnScrollListener , FetchData.FetchDatas {


    PostsAdapter postsAdapter;
    ArrayList<PostImages> postImagesArrayList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[]paths = new String[]{"/sdcard/DCIM/Camera/VID_20220331_132350.mp4"};
    int variable=0;
    Object o;
    public static ArrayList<Object> objectArrayList = new ArrayList<>();
    int currentItems , scrolledOutItems , totalItems;
    boolean isScrolled = false;
    TabLayout tabLayout;
    ViewPager viewPager;
    PostsAdapter2 postsAdapter2;
    ImageFragment2 imageFragment2;
    VideoFragment2 videoFragment2;
    FetchData fetchData;
    RecyclerView postsRecycler;
    MultiPagerAdapter multiPagerAdapter;
    Toolbar toolbar;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_posts, container, false);

        ((MainActivity)getActivity()).ab.setVisibility(View.VISIBLE);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        postsRecycler = (RecyclerView) view.findViewById(R.id.posts_recycler);
        postsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        postsRecycler.setHasFixedSize(true);

        multiPagerAdapter = new MultiPagerAdapter(postImagesArrayList , getActivity());
        postsRecycler.setAdapter(multiPagerAdapter);

        //tabLayout = (TabLayout) view.findViewById(R.id.main_tab);
        //viewPager = (ViewPager) view.findViewById(R.id.main_viewpager);

        //postsAdapter2 = new PostsAdapter2(getChildFragmentManager());

        //imageFragment2 = new ImageFragment2();
        //videoFragment2 = new VideoFragment2();

        //postsAdapter2.addFragments(imageFragment2 , "Images");
        //postsAdapter2.addFragments(videoFragment2 , "Video");

        //viewPager.setAdapter(postsAdapter2);
        //tabLayout.setupWithViewPager(viewPager);

        fetchData = FetchData.getInstance(PostsFragment.this);
        fetchData.fetchData();

//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                if (position == 0) {
//
//                    if (videoFragment2.videoRecycler.videoPlayer != null) {
//
//                        videoFragment2.videoRecycler.videoPlayer.pause();
//
//                    }
//
//                    if (videoFragment2.videoRecycler.myCount != null) {
//
//                        videoFragment2.videoRecycler.myCount = null;
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        return view;
    }





    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //fetchData = FetchData.getInstance(context);
        //fetchData.fetchData();


    }

//    public void getAllPosts(String[] paths) {
//        int i = 0;
//        ArrayList<Content> contents = new ArrayList<>();
//        ArrayList<String> stringArrayList = new ArrayList<>();
//
//        File file = Environment.getExternalStorageDirectory();
//        File file1 = new File(file , "DCIM/Camera");
//
//
//
//        for (File s : file1.listFiles()) {
//
//            if (s.getAbsolutePath().endsWith(".mp4")) {
//
//                i+=1;
//
//                if (i==3){
//
//                    break;
//                }
//
//                contents.add(new Content("video" , s.getAbsolutePath()));
//                stringArrayList.add(s.getAbsolutePath());
//
//            }
//
//        }
//
//        Log.d(PostsFragment.class.getSimpleName(), "getAllPosts: " + stringArrayList.toString());
//
//        PostImages postImages = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//        PostImages postImages1 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//
////        PostImages postImages2 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////        PostImages postImages3 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////
////        PostImages postImages4 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////        PostImages postImages5 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////
////        PostImages postImages6 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////        PostImages postImages7 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////
////        PostImages postImages8 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
////        PostImages postImages9 = new PostImages(UUID.randomUUID().toString() , "ffff" , "ffff" , "ffff" , true , stringArrayList , contents);
//        postImagesArrayList.add(postImages);
//        postImagesArrayList.add(postImages1);
//
////        postImagesArrayList.add(postImages2);
////        postImagesArrayList.add(postImages3);
////
////        postImagesArrayList.add(postImages4);
////        postImagesArrayList.add(postImages5);
////
////        postImagesArrayList.add(postImages6);
////        postImagesArrayList.add(postImages7);
////
////        postImagesArrayList.add(postImages8);
////        postImagesArrayList.add(postImages9);
//
//        postsAdapter.notifyDataSetChanged();
//    }

    @Override
    public void getItemType(Object object) {

       // ExoPlayer player = (ExoPlayer) object;
       // player.play();

    }

    @Override
    public void fetchVideos(Content content) {

        //videoFragment2.getAllPosts(content);
    }

    @Override
    public void fetchImage(Content content) {

    }

    @Override
    public void fetchImages(PostImages content) {

        postImagesArrayList.add(content);
        multiPagerAdapter.notifyDataSetChanged();

        //imageFragment2.getAllPosts(content);

    }

    class AddItems extends AsyncTask<String , Integer , String> {


        @Override
        protected String doInBackground(String... strings) {

            //getAllPosts(paths);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "Videos Loaded", Toast.LENGTH_SHORT).show();
        }
    }



}