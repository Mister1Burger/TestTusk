package com.example.misterburger.testtusk.AdaptersAndListeners;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.misterburger.testtusk.R;
import com.example.misterburger.testtusk.R2;
import com.example.misterburger.testtusk.RealmModule.RealmNews;
import com.example.misterburger.testtusk.model.ArticleStructure;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.UserListViewHolder> {

    private ArrayList<ArticleStructure> myArticles;
    private ArticleListener listener;
    private RealmNews realm;

    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.news_grid)
        LinearLayout linearLayout;
        @BindView(R2.id.name_journal)
        TextView journalName;
        @BindView(R2.id.name_title)
        TextView titleName;
        @BindView(R2.id.image)
        ImageView image;
        @BindView(R2.id.date)
        TextView date;
        @BindView(R2.id.is_favorite)
        MaterialFavoriteButton button;


        UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ArticleAdapter(ArrayList<ArticleStructure> myArticles, RealmNews realm, ArticleListener listener) {
        this.myArticles = myArticles;
        this.listener = listener;
        this.realm = realm;
    }

    private ArrayList<ArticleStructure> getMyArticles() {
        return myArticles;
    }

    @Override
    public ArticleAdapter.UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cellgrid, parent, false);
        UserListViewHolder pvh = new UserListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.UserListViewHolder holder, final int position) {
        holder.journalName.setText(getMyArticles().get(position).getSource().getName());
        holder.titleName.setText(getMyArticles().get(position).getTitle());
        setImage(holder.image, getMyArticles().get(position).getUrlToImage());
        holder.date.setText(getMyArticles().get(position).getPublishedAt());
        holder.button.setFavorite(isFavorite(getMyArticles().get(position)), false);
        holder.button.setOnFavoriteChangeListener((buttonView, favorite) -> {
            boolean bool = isFavorite(getMyArticles().get(position));
            if (bool == true) {
                getMyArticles().get(position).setFavorite(false);
                realm.removeArticle(getMyArticles().get(position));
            } else {
                getMyArticles().get(position).setFavorite(true);
                realm.saveArticle(getMyArticles().get(position));
            }
        });
        holder.linearLayout.setOnClickListener(view -> listener.getArticleListener(getMyArticles().get(position)));
    }

    @Override
    public int getItemCount() {
        return myArticles.size();
    }

    private boolean isFavorite(ArticleStructure article) {
        if (article.isFavorite() == false) {
            return false;
        } else return true;
    }

    private void setImage(ImageView image, String url) {
        new DownloadImageTask(image)
                .execute(url);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        private DownloadImageTask(ImageView bmImage) {
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
