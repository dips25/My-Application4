package com.example.myappication4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myappication4.Models.Users;
import com.example.myappication4.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SerachListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Users> users;

    public SerachListAdapter(ArrayList<Users>users , Context context) {

        this.users = users;
        this.context = context;


    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_list , parent , false);

            viewHolder = new ViewHolder();

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name = (TextView) view.findViewById(R.id.search_listName);
        viewHolder.profileImage = (CircleImageView) view.findViewById(R.id.search_listProfileImage);
        viewHolder.follow = (Button) view.findViewById(R.id.search_follow);

        return view;
    }

    public class ViewHolder{

        TextView name;
        CircleImageView profileImage;
        Button follow;


    }
}
