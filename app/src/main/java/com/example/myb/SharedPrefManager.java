package com.example.myb;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SharedPrefManager {
    private static final String KEY_SELECTED_BOOKS = "selected_books";

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_USER_PROFILES = "user_profiles";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPrefManager(Context context) {
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            gson = new Gson();
        }
    }
 public void saveUserProfile(String username, String password, boolean rememberMe) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
            editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
            editor.apply();
        }
    }

     public void saveUserProfile(String newUserInfo) {
        if (sharedPreferences != null) {
            String existingInfo = sharedPreferences.getString(KEY_USER_PROFILES, "");

            String updatedInfo = existingInfo + newUserInfo + "\n";
  SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USER_PROFILES, updatedInfo);
            editor.apply();
        }
    }
 public List<String> getUserProfileList() {
        List<String> userProfileList = new ArrayList<>();
        if (sharedPreferences != null) {
            String userProfiles = sharedPreferences.getString(KEY_USER_PROFILES, "");

            String[] profileArray = userProfiles.split("\n");

            Collections.addAll(userProfileList, profileArray);
        }
        return userProfileList;
    }

    public User getUserProfile() {
        if (sharedPreferences != null) {
            String username = sharedPreferences.getString(KEY_USERNAME, null);
            String password = sharedPreferences.getString(KEY_PASSWORD, null);
            boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);

            return new User(username, password, rememberMe);
        }
        return null;
    }

    public String getSavedUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getSavedPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }
    public boolean isRememberMeEnabled() {
        return sharedPreferences != null && sharedPreferences.getBoolean(KEY_REMEMBER_ME, true);
    }public void SaveUsernameAndpassword(String username, String password) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
            editor.apply();
        }
    }
    public void saveSelectedBook(Book selectedBook) {
        if (sharedPreferences != null) {
            List<Book> selectedBooks = getSelectedBooks();
            selectedBooks.add(selectedBook);

            String selectedBooksJson = gson.toJson(selectedBooks);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_SELECTED_BOOKS, selectedBooksJson);
            editor.apply();
        }
    }
    public List<Book> getSelectedBooks() {
        List<Book> selectedBooks = new ArrayList<>();
        if (sharedPreferences != null) {
            String selectedBooksJson = sharedPreferences.getString(KEY_SELECTED_BOOKS, "");
  Book[] booksArray = gson.fromJson(selectedBooksJson, Book[].class);

            if (booksArray != null) {
                Collections.addAll(selectedBooks, booksArray);
            }
        }
        return selectedBooks;
    }
}
