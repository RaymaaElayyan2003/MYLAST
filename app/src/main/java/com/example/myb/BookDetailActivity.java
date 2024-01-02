package com.example.myb;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class BookDetailActivity extends AppCompatActivity {
//    Button btnGoToSplashScreen = findViewById(R.id.btnGoToSplashScreen);

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView descriptionTextView;
    private ImageView thumbnailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleTextView = findViewById(R.id.titleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        thumbnailImageView = findViewById(R.id.thumbnailImageView);
        Button btnGoToSplashScreen = findViewById(R.id.btnGoToSplashScreen);
        if (btnGoToSplashScreen != null) {
            btnGoToSplashScreen.setOnClickListener(view -> goToSplashScreen());
        }
        if (getIntent() != null) {
            String bookTitle = getIntent().getStringExtra("bookTitle");
            String bookAuthors = getIntent().getStringExtra("bookAuthors");
            String bookDescription = getIntent().getStringExtra("bookDescription");
            // Assuming you pass the thumbnail resource ID as an int extra
            int thumbnailResourceId = getIntent().getIntExtra("bookThumbnailResourceId", 0);

            if (bookTitle != null && bookAuthors != null && bookDescription != null) {
                 titleTextView.setText(bookTitle);
                authorTextView.setText(bookAuthors);
                descriptionTextView.setText(bookDescription);
                if (thumbnailResourceId != 0) {
                    thumbnailImageView.setImageResource(thumbnailResourceId);
                } else {
                    thumbnailImageView.setImageResource(R.drawable.default_thumbnail);
                }
            } else {
               showErrorAndFinish();

            }
        }
    }
    private void goToSplashScreen() {
        Intent intent = new Intent(BookDetailActivity.this, SplashScreenActivity.class);
        startActivity(intent);
    }
    private void showErrorAndFinish() {
        Snackbar.make(findViewById(android.R.id.content), R.string.error_missing_data, Snackbar.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
