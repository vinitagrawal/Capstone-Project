package me.vinitagrawal.bullets.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import me.vinitagrawal.bullets.BuildConfig;
import me.vinitagrawal.bullets.Constants;
import me.vinitagrawal.bullets.R;
import me.vinitagrawal.bullets.model.Article;
import me.vinitagrawal.bullets.model.Story;
import me.vinitagrawal.bullets.service.ApiService;
import me.vinitagrawal.bullets.service.ArticleDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Map<String, String> queryOptions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setQueryOption(Constants.SORT_BY_KEY, Constants.SORT_BY_TRENDING);
        setQueryOption(Constants.CATEGORY_ID_KEY, Constants.CATEGORY_TRAVEL);
        fetchStories();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.sortRecent) {
            return true;
        } else if (id == R.id.sortTrending) {
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
            case R.id.nav_home :
                break;
            case R.id.nav_news_politics:
                break;
            case R.id.nav_science_technology:
                break;
            case R.id.nav_health_fitness:
                break;
            case R.id.nav_entertainment:
                break;
            case R.id.nav_food_drink:
                break;
            case R.id.nav_travel:
                break;
            case R.id.nav_sports:
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setQueryOption(String name, String value) {
        queryOptions.put(name, value);
    }

    private void fetchStories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(buildGsonConverter())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Story> getStories = apiService.getStories(getHeaderOptions(), queryOptions);
        getStories.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if(response.body()!=null) {
                    Log.d("Articles", response.body().getArticleList().size()+"");
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Map<String, String> getHeaderOptions() {
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.APPLICATION_ID, BuildConfig.THE_AYLIEN_API_ID_VALUE);
        map.put(Constants.APPLICATION_KEY, BuildConfig.THE_AYLIEN_API_KEY_VALUE);
        return map;
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Article.class, new ArticleDeserializer());
        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        return GsonConverterFactory.create(gson);
    }
}
