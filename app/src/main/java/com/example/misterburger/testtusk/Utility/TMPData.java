package com.example.misterburger.testtusk.Utility;

import com.example.misterburger.testtusk.model.ArticleStructure;
import com.example.misterburger.testtusk.model.NewsResponse;

import retrofit2.Call;

/**
 * Created by Burge on 07.02.2018.
 */

public class TMPData {
    private ArticleStructure articleStructure;
    private FragmentFlags flag;
    Call<NewsResponse> call;

    public Call<NewsResponse> getCall() {
        return call;
    }

    public void setCall(Call<NewsResponse> call) {
        this.call = call;
    }

    public FragmentFlags getFlag() {
        return flag;
    }

    public void setFlag(FragmentFlags flag) {
        this.flag = flag;
    }

    public ArticleStructure getArticleStructure() {
        return articleStructure;
    }

    public void setArticleStructure(ArticleStructure articleStructure) {
        this.articleStructure = articleStructure;
    }
}
