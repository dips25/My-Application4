package com.example.myappication4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myappication4.FetchData;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.R;
import com.example.myappication4.VideoPlayerRecycler;
import com.example.myappication4.adapters.ProfileImageAdapter;
import com.example.myappication4.adapters.ProfileVideoAdapter;
import com.example.myappication4.adapters.VideoAdapter;

import java.util.ArrayList;

public class ProfileVideoFragment extends Fragment {

    VideoPlayerRecycler profileVideoRecycler;
    ProfileVideoAdapter profileVideoAdapter;
    VideoAdapter videoAdapter;
    ArrayList<Content> contentArrayList = new ArrayList<>();
    FetchData fetchData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_video , container, false);

        profileVideoRecycler = (VideoPlayerRecycler) view.findViewById(R.id.profile_videorecycler);
        profileVideoRecycler.setMediaObjects(contentArrayList);
        videoAdapter = new VideoAdapter(getActivity() , contentArrayList);
//        profileVideoAdapter = new ProfileVideoAdapter(getActivity() , contentArrayList);
        //profileVideoRecycler.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));
        profileVideoRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileVideoRecycler.setAdapter(videoAdapter);
        profileVideoRecycler.setHasFixedSize(true);

        //fetchData = FetchData.getInstance(ProfileVideoFragment.this);
        //fetchData.fetchProfileData();
        return view;
    }

//    @Override
//    public void fetchVideos(Content content) {
//
//        contentArrayList.add(content);
//        profileVideoAdapter.notifyDataSetChanged();
//
//    }
//
//    @Override
//    public void fetchImages(PostImages content) {
//
//    }
//
//    @Override
//    public void fetchImage(Content content) {
//
//    }
}
