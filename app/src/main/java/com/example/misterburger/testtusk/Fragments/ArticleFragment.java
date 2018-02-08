package com.example.misterburger.testtusk.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.misterburger.testtusk.Controller.Controller;
import com.example.misterburger.testtusk.Main2Activity;
import com.example.misterburger.testtusk.R;
import com.example.misterburger.testtusk.R2;
import com.example.misterburger.testtusk.Utility.TMPData;
import com.example.misterburger.testtusk.model.ArticleStructure;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleFragment extends Fragment {
    @BindView(R2.id.image)
    ImageView image;
    @BindView(R2.id.name_title)
    TextView name_title;
    @BindView(R2.id.is_favorite)
    MaterialFavoriteButton favoriteButton;
    @BindView(R2.id.browser_button)
    Button browser_button;
    @BindView(R2.id.fab)
    FloatingActionButton fab;
    Controller controller;
    TMPData tmpData;
    ArticleStructure article;
    Intent browserIntent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_fragment, container, false);
        ButterKnife.bind(this, view);
        view.setOnTouchListener((v, event) -> true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = ((Main2Activity) getActivity()).getController();
        tmpData = controller.getTmpData();
        article = tmpData.getArticleStructure();
        fab.setOnClickListener(view1 -> {
            tmpData.setFlag(null);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this news!\n" + Uri.parse(article.getUrl()));
            startActivity(Intent.createChooser(shareIntent, "Share with"));
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setImage(image, article.getUrlToImage());
        name_title.setText(article.getTitle());
        browser_button.setOnClickListener(view -> {
            browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(article.getUrl()));
            startActivity(browserIntent);
        });
    }

    private void setImage(ImageView image, String url) {
        new DownloadImageTask(image)
                .execute(url);
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        protected DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
