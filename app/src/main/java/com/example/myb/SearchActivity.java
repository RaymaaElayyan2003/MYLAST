package com.example.myb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private Button btnSearch;
    private ListView listView;
    private BookAdapter bookAdapter;
    private Button btnSwitchApi;
    private VolleyRequestQueueSingleton volleyRequestQueueSingleton;
    SharedPrefManager SharedPrefManager= new SharedPrefManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnSwitchApi = findViewById(R.id.btnSwitchApi);
        listView = findViewById(R.id.listView);

        volleyRequestQueueSingleton = VolleyRequestQueueSingleton.getInstance(this);

        List<Book> bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        listView.setAdapter(bookAdapter);

        btnSwitchApi.setOnClickListener(view -> switchApiSource());

         btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = etSearch.getText().toString().trim();
                if (!searchTerm.isEmpty()) {
                    makeSearchRequest(searchTerm, currentApiSource);
                }
            }
        });
    }

    private void switchApiSource() {
       currentApiSource = (currentApiSource == ApiSource.GOOGLE_BOOKS) ?
                ApiSource.OPEN_LIBRARY : ApiSource.GOOGLE_BOOKS;

        String apiSourceText = (currentApiSource == ApiSource.GOOGLE_BOOKS) ?
                "Google Books API" : "Open Library API";
        showToast("Using " + apiSourceText);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private enum ApiSource {
        GOOGLE_BOOKS,
        OPEN_LIBRARY
    }

    private ApiSource currentApiSource = ApiSource.GOOGLE_BOOKS;
    private void makeSearchRequest(String searchTerm, ApiSource apiSource) {
       String apiUrl = (apiSource == ApiSource.GOOGLE_BOOKS) ?
                "https://www.googleapis.com/books/v1/volumes?q=your_search_query" + searchTerm :
                "https://openlibrary.org/search.json?q=your_search_query " + searchTerm;

        volleyRequestQueueSingleton.makeStringRequest(
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleApiSearchResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleApiError(error);
                    }
                }
        );
    }

    private void handleApiSearchResponse(String response) {
        List<Book> searchResults = parseApiSearchResponse(response);
        updateListView(searchResults);
        showToast("Searching in " + currentApiSource.toString() + " API");

    }

    private void handleApiError(VolleyError error) {
        // Handle API error
        showToast("Error: " + error.toString());
    }

    private List<Book> parseApiSearchResponse(String response) {
        List<Book> results = new ArrayList<>();

        try {
            if (currentApiSource == ApiSource.GOOGLE_BOOKS) {
                results.addAll(parseGoogleBooksApiResponse(response));
            } else {
                results.addAll(parseOpenLibraryApiResponse(response));
            }
        } catch (JSONException e) {
            e.printStackTrace();
          }

        return results;
    }

    private List<Book> parseGoogleBooksApiResponse(String response) throws JSONException {
        List<Book> results = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response);

        JSONArray items = jsonResponse.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
            String title = volumeInfo.getString("title");
            String authors = volumeInfo.optString("authors", "Unknown Author");
            String description = volumeInfo.optString("description", "No description available");
            String thumbnailUrl = volumeInfo.optString("thumbnail", null);
            String source = "Google Books API";
            Book book = new Book(title, authors, description, thumbnailUrl, source);
            results.add(book);
        }

        return results;
    }

    private List<Book> parseOpenLibraryApiResponse(String response) throws JSONException {
        List<Book> results = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response);
        try {

        JSONArray docs = jsonResponse.getJSONArray("docs");
        for (int i = 0; i < docs.length(); i++) {
            JSONObject doc = docs.getJSONObject(i);
            String title = doc.optString("title", "No Title");
            JSONArray authorArray = doc.optJSONArray("author_name");
            String authors = (authorArray != null && authorArray.length() > 0) ? authorArray.getString(0) : "Unknown Author";
            String description = doc.optString("description", "No description available");
            String thumbnailUrl = "";
            String source = "Open Library API";
            Book book = new Book(title, authors, description, thumbnailUrl, source);
            results.add(book);
        }
        } catch (JSONException e) {
            e.printStackTrace();
             }
        return results;
    }

    private void updateListView(List<Book> searchResults) {
        bookAdapter.addAll(searchResults);
        bookAdapter.notifyDataSetChanged();

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Book selectedBook = bookAdapter.getItem(position);
            saveSelectedBook(selectedBook);

            Intent intent = new Intent(SearchActivity.this, BookDetailActivity.class);

            intent.putExtra("bookTitle", selectedBook.getTitle());
            intent.putExtra("bookAuthors", selectedBook.getAuthors());
            intent.putExtra("bookDescription", selectedBook.getDescription());
            startActivity(intent);

            return true;   });
    }
    private void saveSelectedBook(Book selectedBook) {
       SharedPrefManager.saveSelectedBook(selectedBook);
        Toast.makeText(this, "Book saved to your list", Toast.LENGTH_SHORT).show();
    }

    protected void onResume() {
        super.onResume();
        Button btnDirectToBookList = findViewById(R.id.btnDirectToBookList);
        btnDirectToBookList.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, BookDetailActivity.class);
            startActivity(intent);
        });
    }
}
