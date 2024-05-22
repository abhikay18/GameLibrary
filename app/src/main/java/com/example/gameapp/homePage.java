package com.example.gameapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class homePage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Set up the navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setupNavigationDrawer(toolbar);

        // Create an instance of your custom PagerAdapter
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Add fragments to the adapter
        pagerAdapter.addFragment(new TrendingGamesFragment(), "Trending Games");
        pagerAdapter.addFragment(new CategoriesFragment(), "Categories");
        pagerAdapter.addFragment(new RecommendationsFragment(), "Recommendations");

        // Set the adapter to the ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Set up the header view
        setupHeaderView();
    }


    private void setupNavigationDrawer(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    // Handle home navigation
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    // Handle settings navigation
                    return true;
                } else if (itemId == R.id.nav_calculator) {
                    Intent intent = new Intent(homePage.this, Graphics.class);
                    startActivity(intent);
                    return true;
                }
                // Handle other menu item selections
                return false;
            }
        });
    }

    private void setupHeaderView() {
        ImageView imageViewProfile = navigationView.getHeaderView(0).findViewById(R.id.imageViewProfile);
        TextView textViewName = navigationView.getHeaderView(0).findViewById(R.id.fullname_field);
        TextView textViewUsername = navigationView.getHeaderView(0).findViewById(R.id.username_field);

        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");

        textViewName.setText(user_name);
        textViewUsername.setText(user_username);

        // Set profile picture, name, username, and button action
        // For example, load data from shared preferences or a user object
        // imageViewProfile.setImageDrawable(...);
        // textViewName.setText(...);
        // textViewUsername.setText(...);
        // buttonAction.setOnClickListener(...);
    }
}