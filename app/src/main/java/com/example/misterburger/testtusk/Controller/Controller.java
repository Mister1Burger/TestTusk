package com.example.misterburger.testtusk.Controller;

import android.content.Context;

import com.example.misterburger.testtusk.Fragments.ArticleFragment;
import com.example.misterburger.testtusk.Fragments.FavoriteFragment;
import com.example.misterburger.testtusk.Fragments.FiltersFragment;
import com.example.misterburger.testtusk.Fragments.NewsFragment;
import com.example.misterburger.testtusk.RealmModule.RealmNews;
import com.example.misterburger.testtusk.Utility.TMPData;
import com.example.misterburger.testtusk.model.ResponseActive;


/**
 * Created by Burge on 06.02.2018.
 */

public class Controller {
    private RealmNews realmNews;
    private FavoriteFragment favoriteFragment;
    private NewsFragment newsFragment;
    private FiltersFragment filtersFragment;
    private Context context;
    private TMPData tmpData;
    private ArticleFragment articleFragment;
    private ResponseActive active;

    public void init(Context context){
        this.context = context;
        realmNews = new RealmNews(context);
        favoriteFragment = new FavoriteFragment();
        newsFragment = new NewsFragment();
        filtersFragment = new FiltersFragment();
        active = new ResponseActive(context);
        tmpData =  new TMPData();
        articleFragment =  new ArticleFragment();
    }

    public ResponseActive getActive() {
        return active;
    }

    public ArticleFragment getArticleFragment() {
        return articleFragment;
    }

    public TMPData getTmpData() {
        return tmpData;
    }

    public RealmNews getRealmNews() {
        return realmNews;
    }

    public FavoriteFragment getFavoriteFragment() {
        return favoriteFragment;
    }

    public NewsFragment getNewsFragment() {
        return newsFragment;
    }

    public FiltersFragment getFiltersFragment() {
        return filtersFragment;
    }

    public Context getContext() {
        return context;
    }


}
