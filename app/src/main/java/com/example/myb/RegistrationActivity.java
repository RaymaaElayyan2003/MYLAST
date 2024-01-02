package com.example.myb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser();
            }
        });
    }
    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            showToast("Please enter both username and password.");
        } else {
            SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
            sharedPrefManager.saveUserProfile(username, password, true); // Assuming you want to remember the user after registration

            showToast("Registration successful!");

            navigateToUserProfile(username);
        }
    }

    private void navigateToUserProfile(String username) {
        Intent intent = new Intent(RegistrationActivity.this, UserProfileActivity.class);

         intent.putExtra("USERNAME", username);

        startActivity(intent);

            finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
