package com.example.cchiv.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Cchiv on 19/07/2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String url;
    private String querySearch;

    public BookLoader(Context context, String Url, String QuerySearch) {
        super(context);
        url = Url;
        querySearch = QuerySearch;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        String[] strings = url.split("query");
        return QueryUtils.extractBooks(strings[0]+querySearch+strings[1]);
    }
}
