package com.example.myappication4.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myappication4.Models.Content;
import com.example.myappication4.R;
import com.example.myappication4.fragments.ProfileImageFragment;

import java.util.ArrayList;

public class ProfileImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Content> contentArrayList;

    public ProfileImageAdapter(Context context , ArrayList<Content> contentArrayList) {

        this.context = context;
        this.contentArrayList = contentArrayList;



    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_profile_image , parent , false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Content content = contentArrayList.get(position);

        Toast.makeText(context, content.getType(), Toast.LENGTH_SHORT).show();

        Glide.with(context).load(content.getName()).placeholder(R.drawable.user).into(((ImageViewHolder)holder).imageView);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contentArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ImageViewHolder(View view) {

            super(view);

            imageView = (ImageView) view.findViewById(R.id.single_profileImage);


        }
    }
}
