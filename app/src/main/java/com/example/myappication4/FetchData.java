package com.example.myappication4;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.Models.Users;
import com.example.myappication4.fragments.PostsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class FetchData {

    //public static ArrayList<Content> videosList = new ArrayList<>();
    public static ArrayList<Users> userList = new ArrayList<>();
    private static FetchData fetchdata;
    static FetchDatas fetchDatas;
    static Fragment fragment;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FetchData(){

    }

    public static FetchData getInstance(Fragment activity){

        if (fetchdata == null) {

            fetchdata = new FetchData();
        }

        fragment = activity;

        fetchDatas = (FetchDatas) activity;
        return fetchdata;


    }

    public void fetchData() {

        db.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {

                    return;
                }

                for (DocumentChange documentChange : value.getDocumentChanges()) {

                    switch (documentChange.getType()) {

                        case ADDED:

                            PostImages postImages = documentChange.getDocument().toObject(PostImages.class);
                            fetchDatas.fetchImages(postImages);

                    }
                }



            }
        });

    }

    public void fetchUsers() {

        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {

                    return;
                }

                if (!value.isEmpty()) {

                    userList = (ArrayList<Users>) value.toObjects(Users.class);


                }

            }
        });
    }

    public void fetchProfileData(){

        db.collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Posts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            Toast.makeText(fragment.getActivity(), "Error fetching", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (!value.isEmpty()) {

                            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {

                                PostImages postImages = documentSnapshot.toObject(PostImages.class);

                                for (Content content : postImages.getContents()) {

                                    if (content.getType().equalsIgnoreCase("video")) {

                                        fetchDatas.fetchVideos(content);

                                    } else {

                                        fetchDatas.fetchImage(content);
                                    }
                                }
                            }
                        }
                    }
                });

    }

    public interface FetchDatas{

        public void fetchVideos(Content content);
        public void fetchImages(PostImages content);
        public void fetchImage(Content content);
    }
}
