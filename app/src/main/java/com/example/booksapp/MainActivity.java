package com.example.booksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Book> bookList = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDataFromJson();

        setUpList();

        initSearchWidget();

        onClickListener();


    }

    private void onClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book selected = (Book) listView.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), "Book "+ selected.getTitle()+
                        " is chosen at index "+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSearchWidget() {
        SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Book> filtered = new ArrayList<>();
                for (Book book : bookList) {
                    if(book.getTitle().toString().toLowerCase().contains(newText.toLowerCase())){

                        String originalTitle = book.getTitle().toString().toLowerCase();
                        int indexOfFirstLetter = originalTitle.indexOf(newText);
                        int indexOfLastLetter = indexOfFirstLetter+newText.length();
                        if(indexOfFirstLetter != -1) {
                            SpannableStringBuilder ssb = new SpannableStringBuilder(book.getTitle().toString());
                            BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.YELLOW);
                            ssb.setSpan(bcsYellow, indexOfFirstLetter, indexOfLastLetter, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            book.setTitle(ssb);
                            if (!filtered.contains(book))
                                filtered.add(book);
                        }
                    }else{
                        SpannableStringBuilder ssb = new SpannableStringBuilder(book.getTitle().toString());
                        BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.WHITE);
                        ssb.setSpan(bcsYellow, 0, ssb.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        book.setTitle(ssb);
                    }

                    if (book.getAuthor().toString().toLowerCase().contains(newText.toLowerCase())){

                        String originalTitle = book.getAuthor().toString().toLowerCase();
                        int indexOfFirstLetter = originalTitle.indexOf(newText);
                        int indexOfLastLetter = indexOfFirstLetter+newText.length();
                        if(indexOfFirstLetter != -1) {
                            SpannableStringBuilder ssb = new SpannableStringBuilder(book.getAuthor().toString());
                            BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.YELLOW);
                            ssb.setSpan(bcsYellow, indexOfFirstLetter, indexOfLastLetter, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            book.setAuthor(ssb);
                            if (!filtered.contains(book))
                                filtered.add(book);
                        }
                    }else{
                        SpannableStringBuilder ssb = new SpannableStringBuilder(book.getAuthor().toString());
                        BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.WHITE);
                        ssb.setSpan(bcsYellow, 0, ssb.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        book.setAuthor(ssb);

                    }
                }

                BookAdapter adapter = new BookAdapter(getApplicationContext(),0,filtered);
                listView.setAdapter(adapter);

                return true;
            }
        });
    }


    private void setUpList() {
        listView = findViewById(R.id.listView);
        BookAdapter ba = new BookAdapter(getApplicationContext(),0,bookList);
        listView.setAdapter(ba);
    }

    private void setUpDataFromJson() {
        String jsonStr = loadJSONFromAsset();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject element = (JSONObject) data.get(i);
                SpannableStringBuilder title = new SpannableStringBuilder (element.getString("title"));
                SpannableStringBuilder author = new SpannableStringBuilder (element.getString("body"));
                double rating = element.getDouble("rating");
                float r = (float) rating;
                String url = element.getString("url");

                Book b = new Book(title,author,r,url);
                bookList.add(b);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }



    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("books.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}