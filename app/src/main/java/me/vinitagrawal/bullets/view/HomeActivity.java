package me.vinitagrawal.bullets.view;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.vinitagrawal.bullets.BuildConfig;
import me.vinitagrawal.bullets.R;
import me.vinitagrawal.bullets.Utility.Constants;
import me.vinitagrawal.bullets.data.ArticleContract;
import me.vinitagrawal.bullets.data.ArticleDbOperations;
import me.vinitagrawal.bullets.model.Article;
import me.vinitagrawal.bullets.model.Story;
import me.vinitagrawal.bullets.service.ApiService;
import me.vinitagrawal.bullets.service.ArticleDeserializer;
import me.vinitagrawal.bullets.view.adapter.ArticleAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        ArticleAdapter.ItemClickCallback {

    private static final int ARTICLE_LOADER = 0;
    private static final String ARTICLE_SAVEDINSTANCE_KEY = "articleArrayList";
    private Map<String, String> queryOptions = new HashMap<>();

    private String category;
    private TextView noDataTextView;
    private RecyclerView mRecyclerView;
    private ArticleAdapter articleAdapter;

    private ArrayList<Article> articleArrayList = new ArrayList<>();

    private static final String[] ARTICLE_COLUMNS = {
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_ID,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_TITLE,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_SENTENCES,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_CATEGORY,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_MEDIA,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_SOURCE_NAME,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_SOURCE_LOGO_URL,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_AUTHOR,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_PERMALINK,
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_PUBLISHED_AT
    };
    public static final int COL_ARTICLE_ID = 0;
    public static final int COL_ARTICLE_TITLE = 1;
    public static final int COL_ARTICLE_SENTENCES = 2;
    public static final int COL_ARTICLE_CATEGORY = 3;
    public static final int COL_ARTICLE_MEDIA = 4;
    public static final int COL_ARTICLE_SOURCE_NAME = 5;
    public static final int COL_ARTICLE_SOURCE_LOGO_URL = 6;
    public static final int COL_ARTICLE_AUTHOR = 7;
    public static final int COL_ARTICLE_PERMALINK = 8;
    public static final int COL_ARTICLE_PUBLISHED_AT = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noDataTextView = (TextView) findViewById(R.id.info_message);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView.setHasFixedSize(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey(ARTICLE_SAVEDINSTANCE_KEY)) {
            articleAdapter = new ArticleAdapter(this, articleArrayList);
            mRecyclerView.setAdapter(articleAdapter);

            setCategory(Constants.CATEGORY_HOME);
            setQueryOption(Constants.SORT_BY_KEY, Constants.SORT_BY_HOTNESS);
            fetchStoriesFromDb();
        } else {
            articleArrayList = savedInstanceState.getParcelableArrayList(ARTICLE_SAVEDINSTANCE_KEY);
            articleAdapter = new ArticleAdapter(this, articleArrayList);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setAdapter(articleAdapter);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sortHotness) {
            setQueryOption(Constants.SORT_BY_KEY, Constants.SORT_BY_HOTNESS);
            fetchStories();
            return true;
        } else if (id == R.id.sortRecent) {
            setQueryOption(Constants.SORT_BY_KEY, Constants.SORT_BY_RECENCY);
            fetchStories();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                setCategory(Constants.CATEGORY_HOME);
                break;
            case R.id.nav_news_politics:
                setCategory(Constants.CATEGORY_NEWS_AND_POLITICS);
                break;
            case R.id.nav_science_technology:
                setCategory(Constants.CATEGORY_SCIENCE_AND_TECHNOLOGY);
                break;
            case R.id.nav_health_fitness:
                setCategory(Constants.CATEGORY_HEALTH_AND_FITNESS);
                break;
            case R.id.nav_entertainment:
                setCategory(Constants.CATEGORY_ENTERTAINMENT);
                break;
            case R.id.nav_food_drink:
                setCategory(Constants.CATEGORY_FOOD_AND_DRINK);
                break;
            case R.id.nav_travel:
                setCategory(Constants.CATEGORY_TRAVEL);
                break;
            case R.id.nav_sports:
                setCategory(Constants.CATEGORY_SPORTS);
                break;
            default:
                break;
        }
        fetchStoriesFromDb();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARTICLE_SAVEDINSTANCE_KEY, articleArrayList);
    }
    
    private void setQueryOption(String name, String value) {
        queryOptions.put(name, value);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void clearRecyclerView() {
        mRecyclerView.setVisibility(View.GONE);
        articleArrayList.clear();
        articleAdapter.notifyDataSetChanged();
    }

    public void fetchStoriesFromDb() {
        clearRecyclerView();
        getSupportLoaderManager().restartLoader(ARTICLE_LOADER, null, this);
        getSupportLoaderManager().initLoader(ARTICLE_LOADER, null, this);
    }

    private void fetchStories() {
        if (isNetworkAvailable()) {

            clearRecyclerView();
            setQueryOption(Constants.CATEGORY_ID_KEY, category);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(buildGsonConverter())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            Call<Story> getStories = apiService.getStories(getHeaderOptions(), queryOptions);
            getStories.enqueue(new Callback<Story>() {
                @Override
                public void onResponse(Call<Story> call, Response<Story> response) {
                    if (response.body() != null) {
                        articleArrayList.addAll(response.body().getArticleList());
                        if (!articleArrayList.isEmpty()) {
                            Log.d("Articles", articleArrayList.size() + "");
                            mRecyclerView.setVisibility(View.VISIBLE);
                            saveArticles(articleArrayList);
                            articleAdapter.notifyDataSetChanged();
                            noDataTextView.setText(R.string.empty);
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            noDataTextView.setText(R.string.news_unavailable);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Story> call, Throwable t) {
                    noDataTextView.setText(R.string.api_error_message);
                    t.printStackTrace();
                }
            });
        } else {
            if(articleArrayList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                noDataTextView.setText(R.string.network_unavailable);
            }
        }
    }

    private void saveArticles(List<Article> articleList) {
        ArticleDbOperations articleDbOperations = new ArticleDbOperations(this);
        articleDbOperations.deleteArticles(category);
        articleDbOperations.addArticles(articleList, category);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private Map<String, String> getHeaderOptions() {
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.APPLICATION_ID, BuildConfig.THE_NEWS_API_ID_VALUE);
        map.put(Constants.APPLICATION_KEY, BuildConfig.THE_NEWS_API_KEY_VALUE);
        return map;
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Article.class, new ArticleDeserializer());
        Gson gson = gsonBuilder.setDateFormat(Constants.DATE_FORMAT).create();

        return GsonConverterFactory.create(gson);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ArticleContract.ArticleEntry.CONTENT_URI,
                ARTICLE_COLUMNS,
                ArticleContract.ArticleEntry.COLUMN_ARTICLE_CATEGORY + " = ?",
                new String[]{category},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        articleArrayList.clear();
        if(cursor.moveToFirst()) {
            do {
                Article article = Article.fromCursor(cursor);
                articleArrayList.add(article);
            }while (cursor.moveToNext());
        } else {
            fetchStories();
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.scrollToPosition(0);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemSelected(Article article) {
        Toast.makeText(this, article.toString(), Toast.LENGTH_SHORT).show();
    }
}
