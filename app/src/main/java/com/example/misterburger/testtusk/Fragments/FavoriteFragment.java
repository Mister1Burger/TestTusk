package com.example.misterburger.testtusk.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misterburger.testtusk.AdaptersAndListeners.FavoriteArticlesAdapter;
import com.example.misterburger.testtusk.Controller.Controller;
import com.example.misterburger.testtusk.Main2Activity;
import com.example.misterburger.testtusk.R;
import com.example.misterburger.testtusk.R2;
import com.example.misterburger.testtusk.RealmModule.RealmNews;
import com.example.misterburger.testtusk.Utility.FragmentFlags;
import com.example.misterburger.testtusk.Utility.TMPData;
import com.example.misterburger.testtusk.model.ArticleStructure;
import com.example.misterburger.testtusk.model.ResponseActive;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment {
    @BindView(R2.id.swipe_target)
    RecyclerView recyclerView;
    FavoriteArticlesAdapter adapter;
    Controller controller;
    RealmNews realmNews;
    List<ArticleStructure> articles = new ArrayList<>();
    TMPData tmpData;
    ResponseActive active;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_fragment, container, false);
        ButterKnife.bind(this, view);
        controller = ((Main2Activity) getActivity()).getController();
        tmpData = controller.getTmpData();
        realmNews = controller.getRealmNews();
        active = controller.getActive();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
    }

    @Override
    public void onStart() {
        super.onStart();
        articles = realmNews.readArticles();
        adapter = new FavoriteArticlesAdapter(articles, realmNews, article -> {
            tmpData.setArticleStructure(article);
            ((Main2Activity) getActivity()).getFragment(FragmentFlags.ARTICLES_FRAGMENT);
        });
        recyclerView.setAdapter(adapter);
    }
}
