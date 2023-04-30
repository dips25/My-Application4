package com.example.myappication4.adapters;

import android.content.Context;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.OnScrollListener;
import com.example.myappication4.R;
import com.example.myappication4.fragments.PostsFragment;
import com.example.myappication4.fragments.VideoFragment;
import com.google.android.exoplayer2.ExoPlayer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    Context context;
    List<PostImages> postImagesArrayList = new ArrayList<>();
    FragmentManager fragmentManager;
    CountDownTimer countDownTimer;
    private static final String TAG = PostsAdapter.class.getSimpleName();
    public ViewHolder viewHolder;
    PostsFragment postsFragment;







    public PostsAdapter (Context context , ArrayList<PostImages> postsArrayList) {

        this.context = context;
        this.postImagesArrayList = postsArrayList;
        //this.fragmentManager = fragmentManager;
        //this.postsFragment = postsFragment;





    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.single_post_image, parent, false);
            return new ViewHolder(view);

    }

//    @Override
//    public void onViewRecycled(@NonNull PostsAdapter.ViewHolder holder) {
//        super.onViewRecycled(holder);
//        LinearLayout linearLayout = (LinearLayout) (holder.itemView.findViewById(R.id.dots));
//        //holder.pagerAdapter.removeAllFragments();
//        linearLayout.removeAllViews();
//
//        //holder.postImageViewPager = null;
//
//
//    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {

        final PostImages postImages = postImagesArrayList.get(position);

        Log.d(TAG, "onBindViewHolder: " + postImages);

        viewHolder = holder;

        Glide.with(context).load(postImages.getProfileImage()).placeholder(R.drawable.user).into(((ViewHolder) holder).profileImage);
        ((ViewHolder) holder).name.setText(postImages.getName());
        ((ViewHolder) holder).date.setText(postImages.getDate());
        ((ViewHolder) holder).time.setText(postImages.getTime());
        if (postImages.getDescription() != null || postImages.getDescription() != "") {

            holder.description.setText(postImages.getDescription());


        } else {

            holder.description.setVisibility(View.GONE);
        }



        ArrayList<Content> contents = new ArrayList<>();
        for (Content content : postImages.getContents()) {

            if (content.getType().equalsIgnoreCase("image")) {

                contents.add(content);
            }
        }

        Log.d(TAG, "onBindViewHolderCount: " + contents.size());

        if (contents.size()>0) {

            holder.imgs = new ImageView[contents.size()];


            for (int i = 0 ; i<contents.size() ; i++) {

                holder.imgs[i] = new ImageView(context);


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);

                holder.imgs[i].setLayoutParams(params);
                holder.linearLayout.addView(holder.imgs[i]);


                holder.imgs[i].setImageResource(R.drawable.dots_unselected);

            }

            holder.imgs[0].setImageResource(R.drawable.dots_selected);

            holder.pagerAdapter = new ImagePagerAdapter(contents);
            holder.postImageViewPager.setAdapter(holder.pagerAdapter);


        }







        holder.postImageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0 ; i<holder.imgs.length ; i++) {

                    holder.imgs[i].setImageResource(R.drawable.dots_unselected);





                }

                holder.imgs[position].setImageResource(R.drawable.dots_selected);








            }

            @Override
            public void onPageScrollStateChanged(int state) {



            }
        });



    }

    @Override
    public int getItemCount() {
        return postImagesArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView time;
        TextView description;
        CircleImageView profileImage;
        ViewPager postImageViewPager;
        public ImagePagerAdapter pagerAdapter;
        LinearLayout linearLayout;
        ImageView[] imgs;
        FrameLayout postFrame;



        public ViewHolder(View view) {

            super(view);

            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            description = (TextView) view.findViewById(R.id.post_description);
            profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
            postImageViewPager = (ViewPager) view.findViewById(R.id.post_viewpager);
            linearLayout = (LinearLayout) view.findViewById(R.id.dots);


        }
    }


    public class ImagePagerAdapter extends PagerAdapter {

        ArrayList<Content> contents = new ArrayList<>();


        public ImagePagerAdapter(ArrayList<Content> contents) {

            this.contents = contents;


        }
        @Override
        public int getCount() {
            return contents.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            Content content = contents.get(position);

            View view = LayoutInflater.from(context).inflate(R.layout.fragment_image , container , false);

            ImageView   imageView = view.findViewById(R.id.imageview);


            Glide.with(context).load(content.getName()).placeholder(R.drawable.user).into(imageView);


            container.addView(view);

            return (Object) view;


        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

            return view ==  object;




        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((FrameLayout) object);
        }
    }


}
