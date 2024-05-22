package com.example.gameapp;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    // Variables
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    TextView logoText, sloganText;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        // Hooks to all xml elements in activity_signup.xml
        regName = findViewById(R.id.reg_name);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        regBtn.setOnClickListener(v -> {

            if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()){
                return;
            }
            rootNode = FirebaseDatabase.getInstance("https://gameapp-a91c2-default-rtdb.asia-southeast1.firebasedatabase.app");
            reference = rootNode.getReference("users");
            String name = regName.getEditText().getText().toString();
            String username = regUsername.getEditText().getText().toString();
            String email = regEmail.getEditText().getText().toString();
            String phoneNo = regPhoneNo.getEditText().getText().toString();
            String password = regPassword.getEditText().getText().toString();
            UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
            reference.child(phoneNo).setValue(helperClass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Data successfully written
                            Toast.makeText(Signup.this, "Data written successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to write data
                            Toast.makeText(Signup.this, "Failed to write data", Toast.LENGTH_SHORT).show();
                            Log.e("Firebase", "Error writing data to Firebase", task.getException());
                        }
                    });
        });

        // Set OnClickListener for the "Already have an account? LogIn" button
        regToLoginBtn.setOnClickListener(view -> {
            // Create an intent to navigate back to the Login activity
            Intent intent = new Intent(Signup.this, Login.class);

            // Optionally, you can add transition animations
            Pair<View, String>[] pairs = new Pair[7];
            pairs[0] = new Pair<>((View) regToLoginBtn, "button_tran");
            pairs[1] = new Pair<>((View) regName, "username_tran");
            pairs[2] = new Pair<>((View) regPassword, "password_tran");
            pairs[3] = new Pair<>((View) regBtn, "button_tran");
            pairs[4] = new Pair<>((View) regToLoginBtn, "login_signup_tran");
            pairs[5] = new Pair<>((View) logoText, "logo_text");
            pairs[6] = new Pair<>((View) sloganText, "logo_desc");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);
            startActivity(intent, options.toBundle());
        });
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }


}
