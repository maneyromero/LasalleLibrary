package com.example.lasalle.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    ListView favList;
    public static ArrayList<Book> arrayFavs = new ArrayList<>();
    List<Book> books = new ArrayList<>();
    Book book;
    private FavAdapter favAdapter;
    Boolean yaExiste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        this.setTitle("Favorite books");
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        book = bundle.getParcelable("Libro");
        favList = (ListView) findViewById(R.id.favBooks);
        //No conseguimos que no añada los ya existentes, aun comparando los objetos.
         yaExiste = false;
        for (Book bookItem : books) {
            if(bookItem.equals(book)){
                yaExiste=true;
            }
        }

        if(!yaExiste){
            arrayFavs.add(book);
            favAdapter = new FavAdapter(this,arrayFavs);
            favList.setAdapter(favAdapter);

            for (Book book : books) {
                favAdapter.add(book); // Añadimos libros atraves del adapter
            }
        }else{
            Toast.makeText(this, .getString(R.string.favorite_repeat),
                    Toast.LENGTH_LONG).show();
        }

        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayFavs.remove(i);
                favAdapter.notifyDataSetChanged();
            }
        });

    }
}
