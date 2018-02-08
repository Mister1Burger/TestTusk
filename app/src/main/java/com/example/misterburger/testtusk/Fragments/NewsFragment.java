package com.example.misterburger.testtusk.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.misterburger.testtusk.AdaptersAndListeners.ArticleAdapter;
import com.example.misterburger.testtusk.Controller.Controller;
import com.example.misterburger.testtusk.Main2Activity;
import com.example.misterburger.testtusk.R;
import com.example.misterburger.testtusk.R2;
import com.example.misterburger.testtusk.RealmModule.RealmNews;
import com.example.misterburger.testtusk.Utility.FragmentFlags;
import com.example.misterburger.testtusk.Utility.TMPData;
import com.example.misterburger.testtusk.model.ArticleStructure;
import com.example.misterburger.testtusk.model.NewsResponse;
import com.example.misterburger.testtusk.model.ResponseActive;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    @BindView(R2.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R2.id.fab)
    FloatingActionButton fab;
    ArticleAdapter adapter;
    Controller controller;
    RealmNews realmNews;
    ArrayList<ArticleStructure> articles = new ArrayList<>();
    TMPData tmpData;
    ResponseActive active;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
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
        fab.setOnClickListener(view1 -> active.pushArticles(null, null).enqueue(new Callback<NewsResponse>() {

            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {

                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticles();
                    Log.d("TAG", "FIRST " + String.valueOf(articles.size()));
                    adapter = new ArticleAdapter(articles, realmNews, article -> {
                        tmpData.setArticleStructure(article);
                        ((Main2Activity) getActivity()).getFragment(FragmentFlags.ARTICLES_FRAGMENT);
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
            }
        }));

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", tmpData.toString());
        if (tmpData.getCall() != null) {
            tmpData.getCall().enqueue(new Callback<NewsResponse>() {

                @Override
                public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {

                    if (response.isSuccessful() && response.body().getArticles() != null) {

                        if (!articles.isEmpty()) {
                            articles.clear();
                        }

                        articles = response.body().getArticles();
                        Log.d("TAG", "FIRST " + String.valueOf(articles.size()));
                        adapter = new ArticleAdapter(articles, realmNews, article -> {
                            tmpData.setArticleStructure(article);
                            ((Main2Activity) getActivity()).getFragment(FragmentFlags.ARTICLES_FRAGMENT);
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    tmpData.setCall(null);
                    Toast toast = Toast.makeText(controller.getContext(),
                            "Cannot apply filters", Toast.LENGTH_SHORT);
                    toast.show();
                    ((Main2Activity)getActivity()).getFragment(FragmentFlags.NEWS_FRAGMENT);
                }

            });

            tmpData.setCall(null);
        } else {
            active.pushArticles(null, null).enqueue(new Callback<NewsResponse>() {

                @Override
                public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {

                    if (response.isSuccessful() && response.body().getArticles() != null) {

                        if (!articles.isEmpty()) {
                            articles.clear();
                        }

                        articles = response.body().getArticles();
                        Log.d("TAG", "FIRST " + String.valueOf(articles.size()));
                        adapter = new ArticleAdapter(articles, realmNews, article -> {
                            tmpData.setArticleStructure(article);
                            ((Main2Activity) getActivity()).getFragment(FragmentFlags.ARTICLES_FRAGMENT);
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                }
            });
        }
    }
}



