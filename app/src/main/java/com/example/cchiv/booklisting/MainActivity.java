package com.example.cchiv.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Book>>{

    private static String logName = MainActivity.class.getSimpleName();

    private static String urlAPI = "https://www.googleapis.com/books/v1/volumes?q=query&maxResults=10";

    private BookAdapter bookAdapter;

    private LoaderCallbacks<ArrayList<Book>> context = this;

    private static boolean LoaderInitiated = false;

    TextView textView;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Book> bookArrayList = new ArrayList<>();
        bookAdapter = new BookAdapter(getBaseContext(), bookArrayList);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(bookAdapter);

        progressBar = (ProgressBar) findViewById(R.id.loading_screen);

        textView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(textView);

        final Bundle bundle = new Bundle();
        final LoaderManager loaderManager = getLoaderManager();
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            loaderManager.initLoader(1, bundle, context);
        } else {
            textView.setText("No Internet Connection");
        }

        Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()) {
                    EditText editText = (EditText) findViewById(R.id.edit_query);
                    String querySearch = editText.getEditableText().toString();

                    bundle.putString("querySearch", querySearch);

                    loaderManager.restartLoader(1, bundle, context);
                    LoaderInitiated = true;
                }
            }
        });
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        bookAdapter.clear();
        textView.setText("searching now");

        return new BookLoader(this, urlAPI, args.getString("querySearch"));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> bookArrayList) {
        progressBar.setVisibility(View.GONE);

        if(bookArrayList.isEmpty())
            textView.setText("No books to be found.");
        else
            bookAdapter.addAll(bookArrayList);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        bookAdapter.clear();
    }
}
