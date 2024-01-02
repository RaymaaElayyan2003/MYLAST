package com.example.myb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private ListView listViewNewUsers;
    private TextView textViewUserInfo;
    private Button ButtonToGoToSearchScreen;
    private SharedPrefManager sharedPrefManager;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        listViewNewUsers = findViewById(R.id.listViewNewUsers);
        textViewUserInfo = findViewById(R.id.textViewUsername);
        ButtonToGoToSearchScreen = findViewById(R.id.buttonSearchBook);
        sharedPrefManager = new SharedPrefManager(this);
        user = sharedPrefManager.getUserProfile();

        if (user != null) {
            updateUI(user);
        } else {
            redirectToLoginActivity();
        }
        ButtonToGoToSearchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(UserProfileActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

    }

    private void updateUI(User user) {
        String username = user.getUsername();
        String userInfo = "Username: " + username +
                "\nPassword: " + user.getPassword() +
                "\nRemember Me: " + user.getRememberMe();


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USERNAME") && intent.hasExtra("PASSWORD")) {
            String newUsername = intent.getStringExtra("USERNAME");
            String newPassword = intent.getStringExtra("PASSWORD");

            if (newUsername.equals(username) && newPassword.equals(user.getPassword())) {
                showToast("Welcome, new user!");
                String newUserInfo = "Username: " + newUsername +
                        "\nPassword: " + newPassword +
                        "\nRemember Me: " + user.getRememberMe();

                saveNewUserInfo(newUserInfo);

                displayNewUserList();
            } else {

                textViewUserInfo.setText(userInfo);
            }
        }
    }

    private void saveNewUserInfo(String newUserInfo) {
             sharedPrefManager.saveUserProfile(newUserInfo);
    }

    private void displayNewUserList() {
         List<String> newUserList = sharedPrefManager.getUserProfileList();

        ArrayAdapter<String> newUsersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newUserList);
        listViewNewUsers.setAdapter(newUsersAdapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
