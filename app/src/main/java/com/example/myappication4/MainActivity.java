package com.example.myappication4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.myappication4.activities.LoginActivity;
import com.example.myappication4.activities.RegisterActivity;
import com.example.myappication4.adapters.ViewPagerAdapter;
import com.example.myappication4.fragments.PostsFragment;
import com.example.myappication4.fragments.ProfileFragment;
import com.example.myappication4.fragments.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton postButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    BottomNavigationView bottomNavigationView;
    public AppBarLayout ab;
    public ImageView profileImageBackground;
    CollapsingToolbarLayout collapsingToolbarLayout;
    public CoordinatorLayout mainroot;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainroot = (CoordinatorLayout) findViewById(R.id.mainroot);

        ab = (AppBarLayout) findViewById(R.id.action_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.colapsed_toolbar);
//        collapsingToolbarLayout.setTitle("AppNAem");
        //toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(R.string.app_name);


        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        profileImageBackground = (ImageView) findViewById(R.id.profileimagebackground);



        postButton = (FloatingActionButton) findViewById(R.id.post_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , PostActivity.class);
                startActivity(intent);
            }
        });




    }

    private void setupViewPager() {

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.post);
        tabLayout.getTabAt(1).setIcon(R.drawable.sort);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile);



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {

            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful()) {

                                if (!task.getResult().exists()) {

                                    Intent intent = new Intent(MainActivity.this , RegisterActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else {

                                    SharedPreferences sharedPreferences = getSharedPreferences("profileinfo" , MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    editor.putString("id" , mAuth.getCurrentUser().getUid());
                                    editor.putString("name" , documentSnapshot.get("name").toString());
                                    editor.putString("username" , "@dips25");
                                    editor.putString("dob" , documentSnapshot.get("dob").toString());
                                    editor.putString("bio" , documentSnapshot.get("bio").toString());
                                    editor.putString("profileImage" , documentSnapshot.get("profileImage").toString());

                                    editor.commit();
                                    editor.apply();

                                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame , new PostsFragment()).commit();


                                }


                            } else {

                                Toast.makeText(MainActivity.this, "Unable to fetch posts.", Toast.LENGTH_SHORT).show();





                            }

                        }
                    });
        } else {

            Intent intent = new Intent(MainActivity.this , LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame , new PostsFragment()).commit();
                return true;

            case R.id.search:

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame , new SearchFragment()).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame , new ProfileFragment()).commit();
                return true;

        }
        return false;
    }
}