package com.example.myappication4.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myappication4.Models.Content;
import com.example.myappication4.Models.PostImages;
import com.example.myappication4.MyAppication4;
import com.example.myappication4.PostActivity;
import com.example.myappication4.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PostService extends IntentService {

    Notification.Builder builder;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask storagetask;
    NotificationManager notificationManager;
    private static final String TAG = PostService.class.getSimpleName();


    public PostService() {


        super("PostService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);



        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder = new Notification.Builder(PostService.this , MyAppication4.NOTIFICATION_CHANNEL_ID)
                    .setProgress(100 , 0 , false)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(getString(R.string.uploading_post))
                    .setAutoCancel(false)
                    .setContentText(getString(R.string.upload_post_dialogue));

                     startForeground(1 , builder.build());



        } else {

            builder = new Notification.Builder(PostService.this)
                    .setProgress(100 , 0 , false)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(getString(R.string.uploading_post))
                    .setContentText(getString(R.string.upload_post_dialogue))
                    .setAutoCancel(false);

            startForeground(1 , builder.build());



        }


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ArrayList<String> stringArrayList = intent.getStringArrayListExtra("arraylist");
        ArrayList<Content> contentArrayList = new ArrayList<>();
        boolean isImage = false;
        boolean isVideo = false;

        final List<String> downloadUrls = new ArrayList<>();
        String id = UUID.randomUUID().toString();
        final HashMap map = new HashMap();
        SharedPreferences sharedPreferences = this.getSharedPreferences("profileinfo" , MODE_PRIVATE);
        map.put("id" , id);
        map.put("name" , sharedPreferences.getString("name" , ""));
        map.put("time" , new SimpleDateFormat("hh:mm a").format(new Date()));
        map.put("date" , new SimpleDateFormat("dd/mm/yyyy").format(new Date()));
        map.put("isSelected" , false);
        map.put("profileImage" , sharedPreferences.getString("profileImage" , ""));
        map.put("description" , intent.getStringExtra("description"));



        for (String s : stringArrayList)
        {

            String[] arr = s.split("/");
            String imagename = arr[arr.length-1];

            if (imagename.endsWith(".mp4")) {

                isVideo = true;


            } else {

                isImage = true;

            }

            if (isImage && !isVideo) {

                map.put("type" , "image");

            } else if (!isImage && isVideo) {

                map.put("type" , "video");

            } else {

                map.put("type" , "both");
            }

            FirebaseApp.initializeApp(PostService.this);

           StorageReference reference =  storage.getReference("Posts")
                    .child(imagename);

           storagetask = reference.putFile(Uri.fromFile(new File(s)));

            final boolean finalIsVideo = isVideo;
            storagetask
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                           int totalbytes = (int)snapshot.getTotalByteCount();
                           int transferredbytes = (int) snapshot.getBytesTransferred();
                           int progress = (transferredbytes/totalbytes)*100;

                           builder.setProgress(100 , progress , false);
                           builder.build();

                           notificationManager.notify(1 , builder.build());

                       }
                   })
                   .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
               @Override
               public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                   if (!task.isSuccessful()) {

                       throw  task.getException();
                   }

                   return reference.getDownloadUrl();
               }
           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
               @Override
               public void onComplete(@NonNull Task<Uri> task) {

                   notificationManager.cancel(1);

                   if (finalIsVideo) {

                       contentArrayList.add(new Content("video" , task.getResult().toString()));

                   } else {

                       contentArrayList.add(new Content("image" , task.getResult().toString()));

                   }

                   downloadUrls.add(imagename);



                   map.put("contents" , contentArrayList);
                   map.put("posts" , downloadUrls);

                   if (contentArrayList.size() == stringArrayList.size()) {

                       db.collection("Posts")
                               .document(id)
                               .set(map)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {

                                       if (task.isSuccessful()) {

                                           Toast.makeText(PostService.this, "Post Uploaded", Toast.LENGTH_SHORT).show();




                                       } else {

                                           Toast.makeText(PostService.this, getResources().getString(R.string.error_uploading), Toast.LENGTH_SHORT).show();
                                           Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                       }
                                   }
                               });

                       db.collection("Users")
                               .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                               .collection("Posts")
                               .document(id)
                               .set(map)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {

                                       if (task.isSuccessful()) {

                                           notificationManager.cancel(1);


                                       } else {

                                           Toast.makeText(PostService.this, getResources().getString(R.string.error_uploading), Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               });

                   }




               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {

                   Toast.makeText(PostService.this, getResources().getString(R.string.error_uploading), Toast.LENGTH_SHORT).show();
                   return;
               }
           });
        }






    }
}
