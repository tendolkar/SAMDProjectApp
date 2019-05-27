package com.example.livenewsapp;

import java.io.Serializable;

public class NewsItem implements Serializable {
    public String author;
    public String category;
    String title;
    String link;
    String date;
    String guid;
    String description;
    String image;
    String dcC;
    public String toString(){
return  title;

        }
}
