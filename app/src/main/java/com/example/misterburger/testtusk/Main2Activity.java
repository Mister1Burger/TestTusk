package com.example.misterburger.testtusk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.misterburger.testtusk.Controller.Controller;
import com.example.misterburger.testtusk.Utility.Constants;
import com.example.misterburger.testtusk.Utility.FragmentFlags;
import com.example.misterburger.testtusk.Utility.TMPData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.nav_view)
    NavigationView navigationView;
    @BindView(R2.id.drawer_layout)
    DrawerLayout drawer;
    Controller controller;
    TMPData tmpData;

    public Controller getController() {
        return controller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        controller = new Controller();
        controller.init(this);
        tmpData = controller.getTmpData();
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getFragment(FragmentFlags.NEWS_FRAGMENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            getFragment(FragmentFlags.NEWS_FRAGMENT);

        } else if (id == R.id.nav_filters) {
            getFragment(FragmentFlags.FILTERS_FRAGMENT);

        } else if (id == R.id.nav_favorite) {
            getFragment(FragmentFlags.FAVORITES_FRAGMENT);

        } else if (id == R.id.nav_send) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this app!\n" + Uri.parse(Constants.APP_URL));
            startActivity(Intent.createChooser(shareIntent, "Share with"));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getFragment(FragmentFlags flag) {
        tmpData.setFlag(flag);
        switch (flag) {
            case NEWS_FRAGMENT:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, controller.getNewsFragment())
                        .commit();
                break;
            case FILTERS_FRAGMENT:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, controller.getFiltersFragment())
                        .commit();
                break;
            case FAVORITES_FRAGMENT:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, controller.getFavoriteFragment())
                        .commit();
                break;
            case ARTICLES_FRAGMENT:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, controller.getArticleFragment())
                        .commit();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.getRealmNews().onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFragment(FragmentFlags.NEWS_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        Log.d("TAG",String.valueOf(tmpData.getFlag()));
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (tmpData.getFlag() == FragmentFlags.SHARE_FRAGMENT) {
            getFragment(FragmentFlags.ARTICLES_FRAGMENT);
        }else if(tmpData.getFlag() != FragmentFlags.NEWS_FRAGMENT)    {
            getFragment(FragmentFlags.NEWS_FRAGMENT);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setMessage("Do you want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> finish())
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
