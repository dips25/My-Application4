package com.example.myappication4.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myappication4.FetchData;
import com.example.myappication4.MainActivity;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.R;
import com.example.myappication4.Utils;
import com.example.myappication4.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ProfileFragment extends Fragment implements FetchData.FetchDatas {

    Point point;
    WindowManager windowManager;
    Display display;
    Toolbar toolbar;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    FrameLayout framelayout;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout.LayoutParams params;
    ProfileImageFragment profileImageFragment;
    ProfileVideoFragment profileVideoFragment;
    FetchData fetchData;
    ImageView profileImage;
    TextView name , bio;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).ab.setVisibility(View.GONE);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        name = (TextView) view.findViewById(R.id.name);
        bio = (TextView) view.findViewById(R.id.bio);

        profileImageFragment = new ProfileImageFragment();
        profileVideoFragment = new ProfileVideoFragment();

        viewPager = (ViewPager) view.findViewById(R.id.profile_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragments(profileImageFragment , "Image".toLowerCase());
        viewPagerAdapter.addFragments(profileVideoFragment , "Video".toLowerCase());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.profile_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        fetchData = FetchData.getInstance(ProfileFragment.this);
        fetchData.fetchProfileData();

        getProfileDetails();







//        framelayout = (FrameLayout) view.findViewById(R.id.profile_frame);
//        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        viewPagerAdapter.addFragments(new ProfileImageFragment() , "Image");
//        viewPagerAdapter.addFragments(new ProfileVideoFragment() , "Video");
//
//
//        windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
////        display = windowManager.getDefaultDisplay();
////        point = new Point();
////        display.getSize(point);
////
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
//        int widthPx = displayMetrics.widthPixels;
//        int heightPx = displayMetrics.heightPixels;
//
//        int dpWidth = Utils.getDpFromPixels(getActivity() , widthPx);
//        int dpHeight = Utils.getDpFromPixels(getActivity() , heightPx);
//
//        int finalHeight = dpHeight/2;
//        int finalHeightPx = Utils.getPixelsFromDp(getActivity() , finalHeight);
//
//        Log.d("DPHeight", "onCreateView: " + heightPx);
//        relativeLayout = new RelativeLayout(getActivity());
//        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, finalHeightPx));
//        relativeLayout.setBackgroundColor(getResources().getColor(R.color.pink));
//
//        framelayout.addView(relativeLayout);
//
//        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , finalHeightPx);
//        params.gravity = Gravity.BOTTOM;
//
//        viewPager = new ViewPager(getActivity());
//        viewPager.setId(ViewPager.generateViewId());
//        viewPager.setLayoutParams(params);
//        viewPager.setAdapter(viewPagerAdapter);
//
//        tabLayout = new TabLayout(getActivity());
//
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.setSelectedTabIndicator(R.color.pink);
//        viewPager.addView(tabLayout);
//        tabLayout.setupWithViewPager(viewPager);
//        framelayout.addView(viewPager);




//
//        Log.d("ProfileDisplay", "onCreateView: " + point.x + " " + point.y);
//
//        Log.d("ProfileDisplay", "onCreateView: " + width + " " + height);

        return view;

    }

    @Override
    public void fetchVideos(Content content) {

        profileVideoFragment.contentArrayList.add(content);
        profileVideoFragment.profileVideoRecycler.setMediaObjects(profileVideoFragment.contentArrayList);
        if (profileVideoFragment.videoAdapter != null) {

            profileVideoFragment.videoAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void fetchImages(PostImages content) {

    }

    @Override
    public void fetchImage(Content content) {

        profileImageFragment.contentArrayList.add(content);
        if (profileImageFragment.profileImageAdapter != null) {

            profileImageFragment.profileImageAdapter.notifyDataSetChanged();

        }

    }

    public void getProfileDetails() {

        sharedPreferences = getActivity().getSharedPreferences("profileinfo" , MODE_PRIVATE);

        if (sharedPreferences.getString("name" , "") != null) {

            name.setText(sharedPreferences.getString("name" , ""));
        }

        if (sharedPreferences.getString("profileImage" , "") != null) {

            Glide.with(getActivity()).load(sharedPreferences.getString("profileImage" , "")).into(profileImage);
        }

        if (sharedPreferences.getString("bio" , "") != null) {

            bio.setText(sharedPreferences.getString("bio" , ""));
        }
    }
}