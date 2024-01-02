package com.example.myb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private CheckBox cbRememberMe;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();

        sharedPrefManager = new SharedPrefManager(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

    }

    private void initUI() {
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        cbRememberMe = findViewById(R.id.cbRememberMe);
    }

    private void handleLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (cbRememberMe.isChecked()) {
            sharedPrefManager.SaveUsernameAndpassword(username, password);
        }
        if (sharedPrefManager.isRememberMeEnabled()) {
            String savedUsername = sharedPrefManager.getSavedUsername();
            String savedPassword = sharedPrefManager.getSavedPassword();
            if (username.equals(savedUsername) && password.equals(savedPassword)) {
               displayWelcomeBackMessage(savedUsername);
                return;    }
        } displayWelcomeMessage();
          if (isValidCredentials(username, password)) {
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();  } else {
            showError("Invalid Values of inputs . Please try again.");
        }
    }

    private void displayWelcomeMessage() {
        showToast("Hello to our app! Hope you become our customer.");
    }
    private boolean isValidCredentials(String username, String password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }

    private void showError(String errorMessage) {
          showToast(errorMessage);
    }

    private void displayWelcomeBackMessage(String username) {
        showToast("Welcome back, " + username + "!");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToUserProfile() {
        Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
        startActivity(intent);
        finish();   }
}
