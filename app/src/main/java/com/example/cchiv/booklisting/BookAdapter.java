package com.example.cchiv.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cchiv on 18/07/2017.
 */

public class BookAdapter extends ArrayAdapter<Book>{

    Context context = getContext();

    public BookAdapter(@NonNull Context context, @NonNull List<Book>
            books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);

        Book book = getItem(position);

        TextView textView;

        textView = (TextView) listItemView.findViewById(R.id.title);
        textView.setText(book.getTitle());

        textView = (TextView) listItemView.findViewById(R.id.information);
        textView.setText(book.getTitleInformation());

        return listItemView;
    }
}
