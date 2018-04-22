package com.example.lasalle.library;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Manel on 10/03/2018.
 */

public class FavAdapter extends ArrayAdapter<Book> {
    private static class ViewHolder {
    public ImageView favIvCover;
    public TextView favIvTitle;
    public TextView favTvAuthor;

}
    public FavAdapter(Context context, ArrayList<Book> favBooks) {
        super(context, 0, favBooks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //obtiene los items por posicion
        final Book book = getItem(position);

        //comprueba si existe la view y sobreescribe con el inflate view
        FavAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new FavAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fav_row, parent, false);
            viewHolder.favIvCover = (ImageView) convertView.findViewById(R.id.favBookCover);
            viewHolder.favIvTitle = (TextView) convertView.findViewById(R.id.favTitle);
            viewHolder.favTvAuthor = (TextView) convertView.findViewById(R.id.favAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FavAdapter.ViewHolder) convertView.getTag();
        }

        //ponemos los datos en cada sitio
        viewHolder.favIvTitle.setText(book.getName());
        viewHolder.favTvAuthor.setText(book.getAuthor());
        Picasso.with(getContext()).load(Uri.parse(book.getImageUrl())).into(viewHolder.favIvCover);

        return convertView;
    }
}
