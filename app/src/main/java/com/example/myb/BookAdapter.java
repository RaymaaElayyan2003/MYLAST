// BookAdapter.java
package com.example.myb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
        this.context = context;
        this.bookList = books;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_book_detail, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView authorTextView = convertView.findViewById(R.id.authorTextView);
        TextView sourceTextView = convertView.findViewById(R.id.sourceTextView); // Add this TextView

        if (book != null) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthors());
            sourceTextView.setText("Source: " + book.getSource());

        }

        return convertView;
    }
    public void addBook(Book book) {
        bookList.add(book);
        notifyDataSetChanged();
    }
}
