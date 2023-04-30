package com.example.myappication4.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.bumptech.glide.Glide;
import com.example.myappication4.MainActivity;
import com.example.myappication4.PickDate;
import com.example.myappication4.PostActivity;
import com.example.myappication4.R;
import com.example.myappication4.fragments.DatePickerFragment;
import com.example.myappication4.services.PostService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener , PickDate {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    ImageView camera_gallery;
    TextInputEditText name , dob , bio;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout linearLayout;
    ImageView camera , gallery;
    Uri finalUri;
    Button save;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CircleImageView profileImage;
    
    ProgressDialog progressDialog;
    DatePicker datePicker;
    TextInputLayout layoutdob;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        camera_gallery = (ImageView) findViewById(R.id.button_camera_gallery);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        datePicker.setVisibility(View.GONE);

        name = (TextInputEditText) findViewById(R.id.edt_name);
        dob = (TextInputEditText) findViewById(R.id.edt_dob);
        bio = (TextInputEditText) findViewById(R.id.edt_bio);
        layoutdob = (TextInputLayout) findViewById(R.id.layout_dob);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        
        
        save = (Button) findViewById(R.id.register);
        linearLayout = (LinearLayout) findViewById(R.id.bottomsheet_holder);
        camera = (ImageView) findViewById(R.id.camera);
        gallery = (ImageView) findViewById(R.id.gallery);

        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        save.setOnClickListener(this);
        layoutdob.setOnClickListener(this);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);


        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        camera_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            String path = getPath(uri);
            finalUri = uri;
            Glide.with(this).load(path).placeholder(R.drawable.user).into(profileImage);
            File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File file1 = new File(file , "Gallery");

            if (!file1.exists()) {

                file1.mkdir();
            }

            String imageName = new SimpleDateFormat("ddmmyyyy_hhmmss").format(new Date()) + UUID.randomUUID().toString()+".jpg";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(file1 , imageName));
                InputStream inputStream = getContentResolver().openInputStream(uri);
                int i = 0;

                while ((i=inputStream.read(bytes , 0 , bytes.length))!=-1) {

                    baos.write(bytes , 0 , i);
                }

                fileOutputStream.write(baos.toByteArray());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {

                e.printStackTrace();


            }


        } else if (requestCode == 200 && resultCode == RESULT_OK) {

            Glide.with(this).load(finalUri).placeholder(R.drawable.user).into(profileImage);

        }
    }

    private File createImageFile() {

        File file = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file1 = new File(file , "Camera");

        if (!file1.exists()) {

            file1.mkdir();
        }

        String imagename = new SimpleDateFormat("ddmmyy_hhmmss").format(new Date()) + UUID.randomUUID().toString() + ".jpg";
        File finalFile = new File(file1 , imagename);
        return finalFile;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.gallery:

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                break;

            case R.id.camera:

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {

                    File cameraFile = createImageFile();

                    finalUri = FileProvider.getUriForFile(RegisterActivity.this, "com.example.myappication4.fileprovider", cameraFile);

                }

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, finalUri);
                startActivityForResult(cameraIntent, 200);
                break;

            case R.id.register:

                if (name.getText().toString().trim()=="") {

                    Toast.makeText(this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                    break;

                } else if (dob.getText().toString().trim() == "") {

                    Toast.makeText(this, getString(R.string.enter_date_of_birth), Toast.LENGTH_SHORT).show();
                    break;

                } else if (bio.getText().toString().trim() == "") {

                    Toast.makeText(this, getString(R.string.enter_bio), Toast.LENGTH_SHORT).show();
                    break;

                }

                new MySaveTask().execute();
                break;

            case R.id.layout_dob:

                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager() , "DatePickerFragment");
                break;






        }
    }

    public String getPath (Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri , proj , null , null , null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;

    }

    @Override
    public void pickDate(String date) {

        dob.setText(date);
    }

    class MySaveTask extends AsyncTask<String , Integer , String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: " + finalUri);

//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            InputStream inputStream = null;
//
//            try {
//
//                inputStream = getContentResolver().openInputStream(finalUri);
//
//
//
//                byte[] bytes = new byte[2048];
//
//
//                int i = 0;
//
//                while ((i = inputStream.read(bytes , 0  , bytes.length))!=-1) {
//
//                    baos.write(bytes , 0 , i);
//
//                }
//
//            } catch (FileNotFoundException e) {
//
//            } catch (IOException e) {
//
//
//            }




            

            String imageName = new SimpleDateFormat("ddmmyyyy_hhmmss").format(new Date())+UUID.randomUUID().toString()+".jpg";

            StorageReference reference =  firebaseStorage.getReference("ProfileImages")
                    .child(imageName);

            UploadTask storagetask = reference.putFile(finalUri);

            storagetask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    //setProgress((int)(snapshot.getBytesTransferred()/snapshot.getTotalByteCount())*100);

                }
            })
                   
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }

                            return reference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task1) {
                    
                    Map map = new HashMap();
                    map.put("id" , mAuth.getCurrentUser().getUid());
                    map.put("name" , name.getText().toString());
                    map.put("username" , "@dips25");
                    map.put("dob" , dob.getText().toString());
                    map.put("bio" , bio.getText().toString());
                    map.put("profileImage" , task1.getResult().toString());
                    
                    db.collection("Users")
                            .document(mAuth.getCurrentUser().getUid())
                            .set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    SharedPreferences sharedPreferences = getSharedPreferences("profileinfo" , MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("id" , mAuth.getCurrentUser().getUid());
                                    editor.putString("name" , name.getText().toString());
                                    editor.putString("username" , "@dips25");
                                    editor.putString("dob" , dob.getText().toString());
                                    editor.putString("bio" , bio.getText().toString());
                                    editor.putString("profileImage" , task1.getResult().toString());

                                    editor.commit();
                                    editor.apply();

                                    Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            return;
                        }
                    });

                    
                    


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error_uploading), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
            
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();


        }
    }


    }

