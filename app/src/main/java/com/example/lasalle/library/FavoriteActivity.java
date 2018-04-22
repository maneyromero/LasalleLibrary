package com.example.lasalle.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        Button main = (Button) findViewById(R.id.goMain);
        favAdapter = new FavAdapter(this,arrayFavs);
        favList = (ListView) findViewById(R.id.favBooks);
        favList.setAdapter(favAdapter);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainScreen);
            }
        });


        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if(bundle!=null){
            book = bundle.getParcelable("Libro");


            //No conseguimos que no añada los ya existentes, aun comparando los objetos.
            yaExiste = false;
            for (Book bookItem : books) {
                if(bookItem.equals(book)){
                    yaExiste=true;
                }
                //Esta verificación no está funcionando, hay que mirar porqué debugeando
                if(bookItem.getAuthor().toString() == book.getAuthor().toString() && bookItem.getName().toString() == book.getName().toString()){
                    Log.d(" ERROR",bookItem.getName().toString());
                    Log.d(" ERROR", book.getName().toString());
                    yaExiste=true;
                }
            }

            if(!yaExiste){
                arrayFavs.add(book);



                for (Book book : books) {
                    favAdapter.add(book); // Añadimos libros atraves del adapter
                }
            }else{
                Toast.makeText(this, R.string.alreadyInFav,
                        Toast.LENGTH_LONG).show();
            }
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
