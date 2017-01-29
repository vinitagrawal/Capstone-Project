package me.vinitagrawal.bullets.data;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import me.vinitagrawal.bullets.data.ArticleContract.ArticleEntry;
import me.vinitagrawal.bullets.model.Article;

import static me.vinitagrawal.bullets.Utility.Constants.ARRAY_DIVIDER;
import static me.vinitagrawal.bullets.Utility.Utility.convertDateToString;

public class ArticleDbOperations {

    private Context mContext;

    public ArticleDbOperations(Context mContext) {
        this.mContext = mContext;
    }

    public void addArticles(List<Article> articleList, String category) {
        for (Article article: articleList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_ID, article.getArticleId());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_TITLE, article.getTitle());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_MEDIA, article.getMedia());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_SENTENCES, getSentencesAsString(article.getSentences()));
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_SOURCE_NAME, article.getSourceName());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_SOURCE_LOGO_URL, article.getSourceLogoUrl());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_AUTHOR, article.getAuthor());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_PERMALINK, article.getPermalink());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_PUBLISHED_AT, convertDateToString(article.getPublishedAt()));
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_CATEGORY, category);

            mContext.getContentResolver().insert(ArticleEntry.CONTENT_URI, contentValues);
        }

    }

    public void deleteArticles(String category) {
        mContext.getContentResolver().delete(ArticleEntry.CONTENT_URI,
                ArticleEntry.COLUMN_ARTICLE_CATEGORY + " = ?", new String[]{category});
    }

    private String getSentencesAsString(List<String> sentences) {
        if(sentences!=null) {
            return TextUtils.join(ARRAY_DIVIDER, sentences);
        } else {
            return null;
        }
    }

}
