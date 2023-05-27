package com.example.myappication4.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myappication4.MainActivity;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.MultiViewPager;
import com.example.myappication4.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultiPagerAdapter extends RecyclerView.Adapter<MultiPagerAdapter.ViewHolder> {

    ArrayList<PostImages> postImages = new ArrayList<>();
    Context context;
    ViewHolder viewHolder;
    ImageView[]dots;
    Animation animation;

    public MultiPagerAdapter(ArrayList<PostImages> postImages , Context context){

        this.postImages = postImages;
        this.context = context;
        this.animation = AnimationUtils.loadAnimation(context , R.anim.bounce);


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_video , parent , false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PostImages postImages = this.postImages.get(position);

        dots = new ImageView[postImages.getContents().size()];

        for (int i = 0 ; i< dots.length ; i++) {

            dots[i] = new ImageView(context);
            dots[i].setImageResource(R.drawable.dots_unselected);
            holder.dotsView.addView(dots[i]);
        }

        dots[0].setImageResource(R.drawable.dots_selected);

        viewHolder = holder;

        holder.name.setText(postImages.getName());
        holder.description.setText(postImages.getDescription());
        holder.date.setText(postImages.getDate());
        holder.time.setText(postImages.getTime());

        Glide.with(context).load(postImages.getProfileImage()).placeholder(R.drawable.user).into(holder.circleImageView);

        holder.multiViewPager.setMediaObjects((ArrayList<Content>) postImages.getContents());
        holder.multiViewPager.setAdapter(new SamplePagerAdapter(context , (ArrayList<Content>) postImages.getContents()));

        holder.multiViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0 ; i< dots.length ; i++) {

                    dots[i].setImageResource(R.drawable.dots_unselected);

                }

                dots[position].setImageResource(R.drawable.dots_selected);



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        FirebaseFirestore.getInstance().collection("Posts")
                .document(postImages.getId())
                .collection("Likes")
                .document(context.getSharedPreferences("profileinfo" , Context.MODE_PRIVATE).getString("id" , ""))
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (value.exists()) {

                            holder.like.setSelected(true);

                        }
                    }
                });


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isSelected = ((ImageView)v).isSelected();

                setLike(postImages , holder);

//                if (!isSelected) {
//
////                    ((ImageView) v).setSelected(true);
////                    changeColor(holder);
////                    ((ImageView) v).startAnimation(animation);
//
//                    setLike(postImages , holder);
//
//                } else {
//
////                    ((ImageView) v).setSelected(false);
//                }
            }
        });

    }

    private void setLike(PostImages postImages , ViewHolder holder) {

        Map map = new HashMap();
        map.put("email" , context.getSharedPreferences("profileinfo" , Context.MODE_PRIVATE).getString("email" , ""));
        map.put("id" , context.getSharedPreferences("profileinfo" , Context.MODE_PRIVATE).getString("id" , ""));
        map.put("name" , context.getSharedPreferences("profileinfo" , Context.MODE_PRIVATE).getString("name" , ""));

        FirebaseFirestore.getInstance().collection("Posts")
                .document(postImages.getId())
                .collection("Likes")
                .document(context.getSharedPreferences("profileinfo" , Context.MODE_PRIVATE).getString("id" , ""))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            if (!task.getResult().exists()) {

                                DocumentSnapshot documentSnapshot = task.getResult();
                                documentSnapshot.getReference().set(map);

                                holder.like.setSelected(true);
                                changeColor(holder);
                                holder.like.startAnimation(animation);

                            } else {

                                task.getResult().getReference().delete();

                                holder.like.setSelected(false);

                            }
                        } else {

                            Toast.makeText(context, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void changeColor(ViewHolder holder) {
        ColorDrawable[] colorDrawables = new ColorDrawable[]{new ColorDrawable(Color.rgb(  252,184 , 226)),new ColorDrawable(Color.WHITE),new ColorDrawable(Color.RED)};

        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);

        holder.view.setBackground(transitionDrawable);
        transitionDrawable.startTransition(2000);
        //layout.setBackground(getResources().getDrawable(R.color.white));
    }

    @Override
    public int getItemCount() {
        return postImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public MultiViewPager multiViewPager;
        TextView name , description , date , time;
        CircleImageView circleImageView;
        LinearLayout dotsView;
        ImageView like;
        View view;

        public ViewHolder(View view) {

            super(view);

            this.view = view;

            multiViewPager = (MultiViewPager) view.findViewById(R.id.multiviewpager);
            circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
            //parentView = itemView;
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.post_description);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            //rootView = itemView.findViewById(R.id.root_layout);
            dotsView = (LinearLayout) view.findViewById(R.id.dots_view);
            like = (ImageView) view.findViewById(R.id.like);
        }
    }

    public class SamplePagerAdapter extends PagerAdapter{

        ArrayList<Content> contentArrayList = new ArrayList<>();
        Context context;
        SimpleExoPlayer exoPlayer;
        PlayerView playerView;


        public SamplePagerAdapter(Context context, ArrayList<Content> contents) {

            this.contentArrayList = contents;
            this.context = context;


        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            Log.d(MultiPagerAdapter.class.getSimpleName(), "instantiateItem: " + position);

//            FrameLayout frameLayout = new FrameLayout(context);

             FrameLayout layout = new FrameLayout(context);
             layout.addView(viewHolder.multiViewPager.addNewView(position));



//            if (container.getChildCount() > 0) {
//
//                container.removeViewAt(0);
//
//
//            }

            container.addView(layout);
            return layout;

        }


        @Override
        public int getCount() {
            return contentArrayList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public void releasePlayer() {

            if (exoPlayer != null) {

                exoPlayer.release();
                exoPlayer = null;
            }
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);
        }
    }


}
