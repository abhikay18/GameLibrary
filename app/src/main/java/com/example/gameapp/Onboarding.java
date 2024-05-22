package com.example.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;

public class Onboarding extends AppCompatActivity {

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    Button btnGetStarted, btnlogin;
    CheckBox checkBox;

    private static final long DELAY_MS = 4000; // Delay between slides in milliseconds (2 seconds)
    private Handler handler;
    private Runnable runnable;
    private int currentPosition = 0; // Keep track of the current position

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);

        mSLideViewPager = findViewById(R.id.slideViewPage);
        mDotLayout = findViewById(R.id.indicator_layout);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        btnlogin = findViewById(R.id.loginbtn);
        checkBox = findViewById(R.id.checkBox);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    btnGetStarted.setEnabled(true);
                    btnGetStarted.setAlpha(1.0f); // Set full opacity
                } else {
                    btnGetStarted.setEnabled(false);
                    btnGetStarted.setAlpha(0.5f); // Set half opacity (fade effect)
                }
            }
        });

        // Add onClickListener to the "Get Started" button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity here
                Intent intent = new Intent(Onboarding.this, Signup.class);
                startActivity(intent);
                // Finish current activity if needed
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity here
                Intent intent = new Intent(Onboarding.this, Login.class);
                startActivity(intent);
                // Finish current activity if needed
                finish();
            }
        });

        // Initialize the Handler and Runnable for automatic slide change
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Move to the next slide
                currentPosition = (currentPosition + 1) % 4; // Replace 4 with the total number of slides
                mSLideViewPager.setCurrentItem(currentPosition, true);

                // Repeat the process after the delay
                handler.postDelayed(this, DELAY_MS);
            }
        };

        // Start the slide show
        handler.postDelayed(runnable, DELAY_MS);
    }

    public void setUpindicator(int position) {
        dots = new TextView[4];
        mDotLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the callbacks to stop the slide show
        handler.removeCallbacks(runnable);
    }
}