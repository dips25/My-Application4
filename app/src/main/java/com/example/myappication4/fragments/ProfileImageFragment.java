package com.example.myappication4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myappication4.FetchData;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.R;
import com.example.myappication4.adapters.ProfileImageAdapter;

import java.util.ArrayList;

public class ProfileImageFragment extends Fragment {

    RecyclerView profileImageRecycler;
    ProfileImageAdapter profileImageAdapter;
    ArrayList<Content>contentArrayList = new ArrayList<>();
    FetchData fetchData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_image , container, false);

        profileImageRecycler = (RecyclerView) view.findViewById(R.id.profile_imageRecycler);
        profileImageAdapter = new ProfileImageAdapter(getActivity() , contentArrayList);
        profileImageRecycler.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));
        profileImageRecycler.setAdapter(profileImageAdapter);
        //profileImageRecycler.setHasFixedSize(true);

//        fetchData = FetchData.getInstance(ProfileImageFragment.this);
//        fetchData.fetchProfileData();
        return view;
    }

//    @Override
//    public void fetchVideos(Content content) {
//
//    }
//
//    @Override
//    public void fetchImages(PostImages content) {
//
//
//
//    }
//
//    @Override
//    public void fetchImage(Content content) {
//
//        contentArrayList.add(content);
//        profileImageAdapter.notifyDataSetChanged();
//
//    }
}
