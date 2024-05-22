// UserProfile.java
package com.example.gameapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
// UserProfile.java
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
// UserProfile.java
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class UserProfile extends AppCompatActivity {

    TextInputEditText fullName, email, phoneNo, password;

    TextView fullNameLabel, usernameLabel;
    private Button updateButton, calcuBtn, graps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Find the views
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);
        fullName = findViewById(R.id.full_name_profile_edit);
        email = findViewById(R.id.email_profile_edit);
        phoneNo = findViewById(R.id.phone_no_profile_edit);
        password = findViewById(R.id.password_profile_edit);
        updateButton = findViewById(R.id.update_button);
        calcuBtn = findViewById(R.id.calc);
        graps = findViewById(R.id.grap);

        // Show the user data
        showAllUserData();

        // Set up the click listener for the button
        updateButton.setOnClickListener(v -> {
            // Get the text of the button
            CharSequence buttonText = updateButton.getText();

            // Apply the bounce animation to the button text
            Animation textBounceAnimation = AnimationUtils.loadAnimation(this, R.anim.letter_bounce_anim);
            updateButton.startAnimation(textBounceAnimation);

            // Perform other actions if needed
            // ...
        });

        // Set up the click listeners for other buttons
        calcuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfile.this, Calculator.class);

            // You can pass any data to the next activity if needed
            // For example, you can pass the username
            intent.putExtra("username", usernameLabel.getText().toString());

            // Start the next activity
            startActivity(intent);
            // Update the user data here
            // ...
        });

        graps.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfile.this, Graphics.class);
            startActivity(intent);
        });
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phoneNo");
        String user_password = intent.getStringExtra("password");

        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullName.setText(user_name);
        email.setText(user_email);
        phoneNo.setText(user_phoneNo);
        password.setText(user_password);
    }
}
