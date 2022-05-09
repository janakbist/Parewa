package com.example.parewa;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    SearchFragment searchFragment = new SearchFragment();
    AccountFragment accountFragment = new AccountFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    AddPostFragment addPostFragment = new AddPostFragment();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

//        HomeFragment homeFragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.container,homeFragment).commit();

        //Top navigation bar

//        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar) ;
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                            HomeFragment homeFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        break;
                    case R.id.notification:
                        transaction.replace(R.id.container,notificationFragment);
                        transaction.commit();
                        break;
                    case R.id.search:
                        transaction.replace(R.id.container,searchFragment);
                        transaction.commit();
                        break;

                    case R.id.profile:
                        transaction.replace(R.id.container,profileFragment);
                        transaction.commit();
                        break;
                    case R.id.addPost:
                        transaction.replace(R.id.container,addPostFragment);
                        transaction.commit();
                        break;

                }
                return true;
            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menus,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_chat:
//                break;
//            case R.id.action_headlineicon:
//                mAuth.signOut();
//                Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            //default back button
//            case android.R.id.home:
//                finish();
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
    }
