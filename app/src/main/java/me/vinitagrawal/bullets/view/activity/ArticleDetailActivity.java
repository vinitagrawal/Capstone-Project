package me.vinitagrawal.bullets.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.vinitagrawal.bullets.BulletAnalytics;
import me.vinitagrawal.bullets.R;
import me.vinitagrawal.bullets.model.Article;

import static me.vinitagrawal.bullets.Utility.Utility.getRelativeDate;

public class ArticleDetailActivity extends AppCompatActivity {

    public static final String ARTICLE_INTENT_KEY = "ARTICLE";
    private static final String TAG = ArticleDetailActivity.class.getSimpleName();
    private Article article;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle bundle = getIntent().getExtras();
        article = bundle.getParcelable(ARTICLE_INTENT_KEY);

        TextView titleTextView = (TextView) findViewById(R.id.article_title);
        ImageView articleImageView = (ImageView) findViewById(R.id.app_bar_image);
        ImageView sourceImageView = (ImageView) findViewById(R.id.source_image);
        TextView sourceTextView = (TextView) findViewById(R.id.source_name);
        TextView timeTextView = (TextView) findViewById(R.id.post_time);
        TextView authorTextView = (TextView) findViewById(R.id.article_author);
        TextView bulletsTextView = (TextView) findViewById(R.id.article_bullet_points);

        Picasso.with(this).load(article.getMedia())
                .into(articleImageView);

        if(!article.getSourceLogoUrl().isEmpty()) {
            Picasso.with(this).load(article.getSourceLogoUrl())
                    .placeholder(R.color.cardview_light_background)
                    .into(sourceImageView);
        } else
            sourceImageView.setVisibility(View.INVISIBLE);

        titleTextView.setText(article.getTitle());
        sourceTextView.setText(article.getSourceName());
        timeTextView.setText(getRelativeDate(article.getPublishedAt()));
        if(!article.getAuthor().isEmpty()) {
            authorTextView.setText(String.format("- By %s", article.getAuthor()));
        }
        bulletsTextView.setText(setBulletPoints(article.getSentences()));

        BulletAnalytics application = (BulletAnalytics) getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + "Article Detail");
        mTracker.setScreenName("Image~" + "Article Detail");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Articles")
                .setAction("Bullets Read")
                .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareArticle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareArticle() {
        String message = "Read \"" + article.getTitle() + "\"\n" + article.getPermalink();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(shareIntent, "Share Via"));
    }

    private String setBulletPoints(List<String> sentences) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String sentence : sentences) {
            stringBuilder.append(getString(R.string.bullet_symbol)).append(sentence).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public void openFullStory(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getPermalink()));
        startActivity(intent);
    }
}
