package com.example.myappication4.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myappication4.FetchData;
import com.example.myappication4.MainActivity;
import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.Models.Users;
import com.example.myappication4.R;
import com.example.myappication4.adapters.SerachListAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements FetchData.FetchDatas {

    View view;
    Toolbar toolbar;
    ListView searchList;
    FetchData fetchData;
    EditText editText;
    ArrayList<Users> users = new ArrayList<>();
    SerachListAdapter serachListAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sort, container, false);
        searchList = (ListView) view.findViewById(R.id.search_list);
        editText = (EditText) view.findViewById(R.id.search_editText);

        serachListAdapter = new SerachListAdapter(users , getActivity());
        searchList.setAdapter(serachListAdapter);


        fetchData = FetchData.getInstance(SearchFragment.this);
        fetchData.fetchUsers();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                for (Users user : fetchData.userList) {



                    if (user.getName().toLowerCase().startsWith(s.toString().toLowerCase())) {

                        if (!users.contains(user)) {

                            users.add(user);

                        }



                        serachListAdapter.notifyDataSetChanged();


                    } else {

                       if (users.contains(user)) {

                           users.remove(user);
                       }

                        serachListAdapter.notifyDataSetChanged();
                    }


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((MainActivity)getActivity()).ab.setVisibility(View.GONE);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void fetchVideos(Content content) {

    }

    @Override
    public void fetchImages(PostImages content) {

    }

    @Override
    public void fetchImage(Content content) {

    }
}