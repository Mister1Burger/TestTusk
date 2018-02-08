package com.example.misterburger.testtusk.model;

import java.util.ArrayList;

/**
 * Created by Burge on 06.02.2018.
 */

public class NewsResponse {
    private ArrayList<ArticleStructure> articles;

    public ArrayList<ArticleStructure> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ArticleStructure> articles) {
        this.articles = articles;
    }
}