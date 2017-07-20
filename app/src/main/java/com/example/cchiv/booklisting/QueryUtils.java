package com.example.cchiv.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Cchiv on 18/07/2017.
 */

public class QueryUtils {

    private static String className = QueryUtils.class.getSimpleName();

    public static ArrayList<Book> extractBooks(String urlString) {
        URL url =  getURL(urlString);

        String output = getContent(url);

        return getContentList(output);
    }

    private static URL getURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException u) {
            Log.v(className, u.toString());
        }
        return url;
    }

    private static String getContent(URL url) {

        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        StringBuilder output = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while(line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException io) {
            Log.v(className, io.toString());
        }

        return output.toString();
    }

    private static ArrayList<Book> getContentList(String output) {
        ArrayList<Book> arrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(output);
            JSONArray items = jsonObject.getJSONArray("items");
            int itemsLength = items.length();
            for(int i = 0; i < itemsLength; i++) {
                JSONObject jsonObject1 = items.getJSONObject(i);
                JSONObject jsonObject2 = jsonObject1.getJSONObject("volumeInfo");

                String title = jsonObject2.getString("title");
                JSONArray jsonArray = jsonObject2.getJSONArray("authors");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("By ");
                for(int g = 0; g < jsonArray.length(); g++) {
                    stringBuilder.append(jsonArray.getString(g) + ", ");
                }

                arrayList.add(new Book(title, stringBuilder.toString()));
            }
        } catch (JSONException je) {
            Log.v(className, je.toString());
        }

        return arrayList;
    }
}
