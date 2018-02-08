package com.example.misterburger.testtusk.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.misterburger.testtusk.TestTusk;
import com.example.misterburger.testtusk.Utility.Constants;
import com.example.misterburger.testtusk.network.ApiClient;
import com.example.misterburger.testtusk.network.ApiInterface;
import com.example.misterburger.testtusk.network.interceptors.OfflineResponseCacheInterceptor;
import com.example.misterburger.testtusk.network.interceptors.ResponseCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

public class ResponseActive {
    Context context;

    public ResponseActive(Context context) {
        this.context = context;
    }

    private String checkSortBy(String sortBy) {
        if (sortBy == null) {
            sortBy = "popularity";
        }
        return sortBy;
    }

    private String checkQuery(String query) {
        if (query == null) {
            query = "music";
        }
        return query;
    }

    public Call<NewsResponse> pushArticles(String query, String sortBy) {
        query = checkQuery(query);
        sortBy = checkSortBy(sortBy);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new ResponseCacheInterceptor());
        httpClient.addInterceptor(new OfflineResponseCacheInterceptor());
        httpClient.cache(new Cache(new File(TestTusk.getTestTusk()
                .getCacheDir(), "ResponsesCache"), 10 * 1024 * 1024));
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        ApiInterface request = ApiClient.getClient(httpClient).create(ApiInterface.class);


        Call<NewsResponse> call = request.getSearchResults(query, sortBy, Constants.LANGUAGE, Constants.KEY_API);
        return call;
    }

    public Call<NewsResponse> filterArticles(String query, String sortBy, String from, String to) {
        query = checkQuery(query);
        sortBy = checkSortBy(sortBy);
        if (from == null || to == null) {
            return null;
        }
        Log.d("TAG", from + " " + to);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new ResponseCacheInterceptor());
        httpClient.addInterceptor(new OfflineResponseCacheInterceptor());
        httpClient.cache(new Cache(new File(TestTusk.getTestTusk()
                .getCacheDir(), "ResponsesCache"), 10 * 1024 * 1024));
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        ApiInterface request = ApiClient.getClient(httpClient).create(ApiInterface.class);

        Call<NewsResponse> call = request.getResultFromPeriod(query, from,to, Constants.LANGUAGE, Constants.KEY_API);
        Log.d("TAG", call.toString());
        return call;
    }

    public Call<NewsResponse> filterArticles(String query, String sortBy, String source) {
        query = checkQuery(query);
        sortBy = checkSortBy(sortBy);
        source = checkSource(source);
        if (source == null) {
            return null;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new ResponseCacheInterceptor());
        httpClient.addInterceptor(new OfflineResponseCacheInterceptor());
        httpClient.cache(new Cache(new File(TestTusk.getTestTusk()
                .getCacheDir(), "ResponsesCache"), 10 * 1024 * 1024));
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        ApiInterface request = ApiClient.getClient(httpClient).create(ApiInterface.class);


        Call<NewsResponse> call = request.getSourceResults(query, sortBy, source, Constants.LANGUAGE, Constants.KEY_API);
        return call;
    }

    private String checkSource(String source) {
        if (source == null) {
            Toast toast = Toast.makeText(context,
                    "Enter the source", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        } else {
            return source;
        }
    }

    public Call<NewsResponse> filterArticles(String query, String sortBy, String source, String from, String to) {
        query = checkQuery(query);
        sortBy = checkSortBy(sortBy);
        source = checkSource(source);
        if (from == null || to == null) {
            return null;
        }
        if (source == null) {
            return null;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new ResponseCacheInterceptor());
        httpClient.addInterceptor(new OfflineResponseCacheInterceptor());
        httpClient.cache(new Cache(new File(TestTusk.getTestTusk()
                .getCacheDir(), "ResponsesCache"), 10 * 1024 * 1024));
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        ApiInterface request = ApiClient.getClient(httpClient).create(ApiInterface.class);

        Call<NewsResponse> call = request.getResultFromAllFilters(query, sortBy, source, from, to, Constants.LANGUAGE, Constants.KEY_API);
        return call;
    }

}
