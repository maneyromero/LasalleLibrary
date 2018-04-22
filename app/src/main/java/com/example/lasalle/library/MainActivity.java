package com.example.lasalle.library;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_QUERY = "android";
    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;

    String searchQuery = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Seteamos las views
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        lvBooks = (ListView) findViewById(R.id.lvBooks);



        //Creamos el bookAdapter i lo volcamos a la lisView
        final ArrayList<Book> aBooks = new ArrayList<>();
        bookAdapter = new BookAdapter(this, aBooks);
        lvBooks.setAdapter(bookAdapter);

        //Por defecto cojemos los libros que sean de android
        fetchBooks(DEFAULT_QUERY);

        //Cuando seleccionamos una fila nos redirije a SelectedBook y deveria mostrar el titulo y la descripcion como minimo xd
        //De  momento esto lo unico qwur hace es abrir una activity nueva
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                Book book = aBooks.get(i);


                bundle.putParcelable("Libro", book);

                Intent newActivity = new Intent(MainActivity.this,SelectedBook.class);
                newActivity.putExtra("Libro",book);
                startActivity(newActivity);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú; esto agrega elementos a la barra de acción si está presente
        getMenuInflater().inflate(R.menu.options_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                fetchBooks(query);
                searchQuery = query;
                // Reseteamos la busqueda
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();

                //seteamos el titulo de la actividad por lo que introduce el user a buscar
                MainActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }


    //llamamos a la Api i parsea los resultados

    //lo comvertimos a un arrayy u los añade en el adapter
    private void fetchBooks(String query) {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new BookClient().execute(query);

        }

    }

    public class BookClient extends AsyncTask<String, Void, JSONObject> {

        private static final String API_BASE_URL = "https://www.googleapis.com/";

        @Override
        protected JSONObject doInBackground(String... query) {

            try {
                return downloadUrl(API_BASE_URL + "books/v1/volumes?q=" + query[0]);
            } catch (IOException e) {
                return null;
            }
        }

        //onPostExecute despliega los resultados de el AsynoTasd
        @Override
        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try {

                    int totalItems = result.getInt("totalItems");

                    if(totalItems !=0){

                        //Obtenemos los docs json array
                        JSONArray docs = result.getJSONArray("items");

                        //parseamos el json array a el array del modelo de objetos
                        final ArrayList<Book> books = Book.fromJson(docs);

                        //elimina todos los libros del adapter
                        bookAdapter.clear();

                        //recarga el modelo en el adaptew
                        for (Book book : books) {
                            bookAdapter.add(book); // Añadimos libros atraves del adapter
                        }
                        bookAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(ProgressBar.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


        //Dada una URL, establece una HttpUrlConnection y recupera
                // el contenido de la página web como InputStream, que devuelve como
                // un String.

        private JSONObject downloadUrl(String myurl) throws IOException {
            InputStream is = null;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*los milisegundos*/);
                conn.setConnectTimeout(15000 );
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // starts la query
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();

                return readIt(is);

            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        private JSONObject readIt(InputStream is) {

            JSONObject jsonObject = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

                jsonObject = new JSONObject(sb.toString());

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

    }

}
