package com.example.booksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;


public class BookAdapter extends ArrayAdapter implements Filterable {

    public BookAdapter(Context context, int resource, List<Book> bookList){
        super(context,resource,bookList);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        Book book = (Book) getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_line,parent,false);
        }

        // update book properties
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.titleTitle);
        TextView author = (TextView) convertView.findViewById(R.id.author);
        RatingBar rating = (RatingBar) convertView.findViewById(R.id.ratingBar);

        // using glide to download url image and present it
        Glide.with(getContext())
                .load(book.getUrl())
                .into(img);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        rating.setRating(book.getRating());


        return convertView;
    }
}
