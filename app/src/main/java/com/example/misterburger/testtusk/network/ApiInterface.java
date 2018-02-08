package com.example.misterburger.testtusk.network;

import com.example.misterburger.testtusk.model.ArticleResponse;
import com.example.misterburger.testtusk.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Burge on 06.02.2018.
 */

public interface ApiInterface {

    @GET("articles")
    Call<ArticleResponse> getCall(@Query("source") String source,
                                  @Query("sortBy") String sortBy,
                                  @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getSearchResults(@Query("q") String query,
                                        @Query("sortBy") String sortBy,
                                        @Query("language") String language,
                                        @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getResultFromPeriod(@Query("q") String query,
                                           @Query("from") String from,
                                           @Query("to") String to,
                                           @Query("language") String language,
                                           @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getSourceResults(@Query("q") String query,
                                        @Query("sortBy") String sortBy,
                                        @Query("source") String source,
                                        @Query("language") String language,
                                        @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getResultFromAllFilters(@Query("q") String query,
                                               @Query("sortBy") String sortBy,
                                               @Query("source") String source,
                                               @Query("from") String from,
                                               @Query("to") String to,
                                               @Query("language") String language,
                                               @Query("apiKey") String apiKey);
}
