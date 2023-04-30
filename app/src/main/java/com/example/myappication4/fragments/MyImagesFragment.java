package com.example.myappication4.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myappication4.Models.DirImages;
import com.example.myappication4.R;
import com.example.myappication4.adapters.GridAdapter;

import java.io.File;
import java.util.ArrayList;

public class MyImagesFragment extends Fragment {

    private static final String TAG = MyImagesFragment.class.getSimpleName();

    GridView gridView;
    GridAdapter gridAdapter;
    ArrayList<DirImages> dirImages = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myimages , container , false);

        gridView = (GridView) view.findViewById(R.id.post_grid);
        gridAdapter = new GridAdapter(getActivity() , dirImages);
        gridView.setAdapter(gridAdapter);

        getAllImages();

        return view;
    }

    public void getAllImages() {

        File file = Environment.getExternalStorageDirectory();
        File camerafile = new File(file , "DCIM/Camera");
        File screenrecorder = new File(file , "DCIM/Screenshots");
       // File screenshare = new File(file , "DCIM/ScreenRecorder");
        File whatsapp = new File(file , "WhatsApp/Media/WhatsApp Images");
        File whatsappsent = new File(file , "WhatsApp/Media/WhatsApp Images/Sent");
       // File whatsappvideo = new File(file , "WhatsApp/Media/WhatsApp Video");
        File downloadfile = new File(file , "Download");

        File[] files = {camerafile , screenrecorder , whatsapp , whatsappsent , downloadfile};


        Log.d(TAG, "getAllImages: " + file.getAbsolutePath());

        getFiles(files);


    }

    public void getFiles(File[] file) {

        for (int i = 0 ; i<file.length ; i++) {

            File[] newfile = file[i].listFiles();

            for (int j = 0 ; j<newfile.length ; j++) {

                if (newfile[j].isFile()) {

                    if (newfile[j].getPath().endsWith(".jpg") || newfile[j].getPath().endsWith(".jpeg") || newfile[j].getPath().endsWith(".png"))

                        dirImages.add(new DirImages(Uri.parse(newfile[j].getAbsolutePath()).toString()));



                }
            }


        }



        gridAdapter.notifyDataSetChanged();


    }
}
