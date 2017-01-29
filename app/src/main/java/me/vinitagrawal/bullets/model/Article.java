package me.vinitagrawal.bullets.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.vinitagrawal.bullets.view.HomeActivity;

import static me.vinitagrawal.bullets.Utility.Constants.ARRAY_DIVIDER;
import static me.vinitagrawal.bullets.Utility.Utility.convertStringToDate;
import static me.vinitagrawal.bullets.Utility.Utility.convertDateToString;

public class Article implements Parcelable {

    @SerializedName("id")
    private Integer articleId;
    private String title;
    private String media;
    private String permalink;
    private String sourceName;
    private String sourceLogoUrl;
    private String author;
    private String category;
    private List<String> sentences;
    private Date publishedAt;

    public Article() {
    }

    protected Article(Parcel in) {
        articleId = in.readInt();
        title = in.readString();
        media = in.readString();
        permalink = in.readString();
        sourceName = in.readString();
        sourceLogoUrl = in.readString();
        author = in.readString();
        category = in.readString();
        sentences = in.createStringArrayList();
        publishedAt = convertStringToDate(in.readString());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceLogoUrl() {
        return sourceLogoUrl;
    }

    public void setSourceLogoUrl(String sourceLogoUrl) {
        this.sourceLogoUrl = sourceLogoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private static List<String> getSentencesAsList(String sentences) {
        if(sentences!=null) {
            return Arrays.asList(TextUtils.split(sentences, ARRAY_DIVIDER));
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", media='" + media + '\'' +
                ", permalink='" + permalink + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", sourceLogoUrl='" + sourceLogoUrl + '\'' +
                ", author='" + author + '\'' +
                ", category=" + category +
                ", sentences=" + sentences +
                ", publishedAt=" + publishedAt +
                '}';
    }

    public static Article fromCursor(Cursor cursor) {
        Article article = new Article();
        article.setArticleId(cursor.getInt(HomeActivity.COL_ARTICLE_ID));
        article.setTitle(cursor.getString(HomeActivity.COL_ARTICLE_TITLE));
        article.setSentences(getSentencesAsList(cursor.getString(HomeActivity.COL_ARTICLE_SENTENCES)));
        article.setCategory(cursor.getString(HomeActivity.COL_ARTICLE_CATEGORY));
        article.setMedia(cursor.getString(HomeActivity.COL_ARTICLE_MEDIA));
        article.setSourceName(cursor.getString(HomeActivity.COL_ARTICLE_SOURCE_NAME));
        article.setSourceLogoUrl(cursor.getString(HomeActivity.COL_ARTICLE_SOURCE_LOGO_URL));
        article.setAuthor(cursor.getString(HomeActivity.COL_ARTICLE_AUTHOR));
        article.setPermalink(cursor.getString(HomeActivity.COL_ARTICLE_PERMALINK));
        article.setPublishedAt(convertStringToDate(cursor.getString(HomeActivity.COL_ARTICLE_PUBLISHED_AT)));
        return article;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(articleId);
        dest.writeString(title);
        dest.writeString(media);
        dest.writeString(permalink);
        dest.writeString(sourceName);
        dest.writeString(sourceLogoUrl);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeStringList(sentences);
        dest.writeString(convertDateToString(publishedAt));
    }
}
