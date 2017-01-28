package me.vinitagrawal.bullets.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Article {

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
}
