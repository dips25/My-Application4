package com.example.myappication4.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myappication4.R;
import com.example.myappication4.adapters.ViewPagerAdapter;
import com.example.myappication4.fragments.ImageFragment;
import com.example.myappication4.fragments.VideoFragment;
import com.example.myappication4.services.PostService;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    EditText previewPostDescription;
    TextView name , date , time , description;
    SharedPreferences sharedPreferences;
    Intent intent;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ViewPagerAdapter imagePagerAdapter;
    ViewPager previewViewPager;
    CircleImageView profileImage;
    TextView post;
    String type;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_preview);

        intent = getIntent();
        stringArrayList = intent.getStringArrayListExtra("arraylist");
        previewViewPager = (ViewPager) findViewById(R.id.preview_view_pager);

        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        post = (TextView) findViewById(R.id.post);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        description = (TextView) findViewById(R.id.post_description);

        post.setOnClickListener(this);

        imagePagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        loadVideos();


        previewViewPager.setAdapter(imagePagerAdapter);



        previewViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        previewPostDescription = (EditText) findViewById(R.id.preview_post_description);

        sharedPreferences = getSharedPreferences("profileinfo" , MODE_PRIVATE);

        name.setText(sharedPreferences.getString("name" , ""));
        date.setText(sharedPreferences.getString("date" , ""));
        time.setText(sharedPreferences.getString("time" , ""));

        Glide.with(PostPreviewActivity.this)
                .load(sharedPreferences.getString("profileImage" , ""))
                .placeholder(R.drawable.user)
                .into(profileImage);


        previewPostDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                description.setText(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadVideos() {

        for (String s : stringArrayList) {

            if (s.endsWith("mp4")) {

                imagePagerAdapter.addFragments(new VideoFragment(s) , "Video");
            } else {

                imagePagerAdapter.addFragments(new ImageFragment(s) , "Image");
            }
        }

        previewViewPager.setAdapter(imagePagerAdapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.post:

                 Intent intent = new Intent(this , PostService.class);
                 intent.putExtra("arraylist" , stringArrayList);
                 intent.putExtra("description" , description.getText().toString());
                 ContextCompat.startForegroundService(this , intent);
        }

    }

//    public class ImagePagerAdapter extends PagerAdapter {
//
//        ArrayList<String> stringArrayList = new ArrayList<>();
//
//        public ImagePagerAdapter(ArrayList<String> stringArrayList) {
//
//            this.stringArrayList = stringArrayList;
//
//        }
//
//        @Override
//        public int getCount() {
//            return stringArrayList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return (view == (View) object);
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.fragment_post_preview_image , container  , false);
//            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.post_preview_frame);
//            ImageView imageView = (ImageView) view.findViewById(R.id.post_images);
//            VideoView videoView = (VideoView) view.findViewById(R.id.post_videos);
//
//
//            if (this.stringArrayList.get(position).endsWith(".mp4")) {
//
//                imageView.setVisibility(View.GONE);
//                videoView.setVisibility(View.VISIBLE);
//                videoView.setVideoURI(Uri.parse(this.stringArrayList.get(position)));
//
//                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//
//                        MediaController controller = new MediaController(PostPreviewActivity.this);
//                        controller.setAnchorView(videoView);
//                        videoView.setMediaController(controller);
//                        //mp.seekTo(1000);
//                        mp.start();
//                        mp.pause();
//                    }
//                });
//
//
//
//            } else {
//
//                videoView.setVisibility(View.GONE);
//                imageView.setVisibility(View.VISIBLE);
//                Glide.with(PostPreviewActivity.this).load(this.stringArrayList.get(position)).placeholder(R.drawable.user).into(imageView);
//
//
//            }
//
//            container.addView(view , 0);
//
//
//            return view;
//        }
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//    }
}
