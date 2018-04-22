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


public class BookAdapter extends ArrayAdapter<Book> {

    static class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvAuthor;
    }

    public BookAdapter(Context context, ArrayList<Book> aBooks) {

        super(context, 0, aBooks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //obtiene los items por posicion
        final Book book = getItem(position);

        //comprueva si existe la view y sobrescrive con el inflate view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_row, parent, false);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.ivBookCover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //ponemos los datos en cada sitio
        viewHolder.tvTitle.setText(book.getName());
        viewHolder.tvAuthor.setText(book.getAuthor());
        Picasso.with(getContext()).load(Uri.parse(book.getImageUrl())).into(viewHolder.ivCover);

        return convertView;
    }
}