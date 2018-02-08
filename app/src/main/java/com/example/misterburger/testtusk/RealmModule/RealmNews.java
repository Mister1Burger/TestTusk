package com.example.misterburger.testtusk.RealmModule;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.misterburger.testtusk.model.ArticleStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class RealmNews {

    Context context;

    public RealmNews(Context context) {
        this.context = context;
    }

    private Realm init() {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("reminder.realm")
                .modules(new MyLibraryModule())
                .build();

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Log.e("TAG", String.valueOf(e));
                Realm.deleteRealm(realmConfiguration);
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                Log.e("TAG", String.valueOf(ex));
            }
        }

        return null;
    }


    public List<ArticleStructure> readArticles() {
        Realm realm = init();
        File realmFile = new File(context.getFilesDir(), "reminder.realm");
        try {
            assert realm != null;
            RealmResults<ArticleStructure> list = realm.where(ArticleStructure.class)
                    .findAll();
            Log.d("TAG", String.valueOf(realmFile.length()));
            if (list == null)
                return new ArrayList<>();
            return list;
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }


    public void saveArticle(ArticleStructure article) {
        if (article.isFavorite() == true) {
            Realm realm = init();
            File realmFile = new File(context.getFilesDir(), "reminder.realm");
            assert realm != null;
            try {
                realm.beginTransaction();
                realm.insertOrUpdate(article);
                Log.d("TAG", String.valueOf(realmFile.length()));
                realm.commitTransaction();
                Toast toast = Toast.makeText(context,
                        "Add to favorite", Toast.LENGTH_SHORT);
                toast.show();
            } catch (NullPointerException ignore) {
            }
        }
    }


    public void removeArticle(ArticleStructure article) {
        if (article.isFavorite() == true) {
            Realm realm = init();
            File realmFile = new File(context.getFilesDir(), "reminder.realm");
            assert realm != null;
            try {
                article = realm.where(ArticleStructure.class)
                        .equalTo("url", article.getUrl())
                        .findFirst();
                if (article != null) {
                    realm.beginTransaction();
                    article.deleteFromRealm();
                    realm.commitTransaction();
                }
                Toast toast = Toast.makeText(context,
                        "Unfavorite anymore!", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("TAG", String.valueOf(realmFile.length()));
            } catch (NullPointerException ignore) {
            }
        }
    }


    public void onDestroy() {
        Realm realm = init();
        assert realm != null;
        realm.close();
    }


}
