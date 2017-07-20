package com.example.cchiv.booklisting;

/**
 * Created by Cchiv on 18/07/2017.
 */

public class Book {

    private String title = "";
    private String titleInformation = "";

    public Book(String string1, String string2) {
        title = string1;
        titleInformation = string2;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleInformation() {
        return titleInformation;
    }
}
