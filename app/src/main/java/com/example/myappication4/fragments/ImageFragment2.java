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

import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.R;
import com.example.myappication4.adapters.PostsAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageFragment2 extends Fragment {

    RecyclerView imageRecycler;
    PostsAdapter postsAdapter;
    ArrayList<PostImages> postImagesArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image2 , container , false);
        imageRecycler = (RecyclerView) view.findViewById(R.id.image_recycler);



        return view;
    }

    public void getAllPosts(PostImages postImages) {


        imageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageRecycler.setHasFixedSize(true);
        postImagesArrayList.add(postImages);
        postsAdapter = new PostsAdapter(getActivity() , postImagesArrayList);
        imageRecycler.setAdapter(postsAdapter);
    }
}
