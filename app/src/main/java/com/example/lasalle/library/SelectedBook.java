package com.example.lasalle.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class SelectedBook extends AppCompatActivity {

    TextView titulo,autor ;
    Button fav;
    String imageURL;
    ImageView image;
    Book book;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selected_book);
        this.setTitle("Book Details");

        titulo =(TextView) findViewById(R.id.textView);
        autor =(TextView) findViewById(R.id.textView2);
        fav= (Button) findViewById(R.id.button) ;
        image = (ImageView) findViewById(R.id.imageDetail);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
         book = bundle.getParcelable("Libro");
        titulo.setText(book.getName());
        autor.setText(book.getAuthor());
        Picasso.with(this).load(Uri.parse(book.getImageUrl())).into(this.image);
            fav.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();



                    bundle.putParcelable("Libro", book);

                    Intent newActivity = new Intent(SelectedBook.this,FavoriteActivity.class);
                    newActivity.putExtra("Libro",book);

                    startActivity(newActivity);
                }
            });

    }
}
