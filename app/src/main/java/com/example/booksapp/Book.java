package com.example.booksapp;

import android.graphics.Color;
import android.text.SpannableStringBuilder;

public class Book {

    // book's properties
    private SpannableStringBuilder title;
    private SpannableStringBuilder author;
    private float rating;
    private String url;

    public Book(SpannableStringBuilder title, SpannableStringBuilder author, float rating, String url) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.url = url;
    }


    public SpannableStringBuilder getTitle() {
        return title;
    }

    public void setTitle(SpannableStringBuilder title) {
        this.title = title;
    }

    public SpannableStringBuilder getAuthor() {
        return author;
    }

    public void setAuthor(SpannableStringBuilder author) {
        this.author = author;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
