package me.vinitagrawal.bullets.model;

import java.util.Date;
import java.util.List;

public class Article {

    private Integer id;
    private String title;
    private String media;
    private String permalink;
    private String sourceName;
    private String sourceLogoUrl;
    private String author;
    private List<String> sentences;
    private Date publishedAt;

    public Article() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", media='" + media + '\'' +
                ", permalink='" + permalink + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", sourceLogoUrl='" + sourceLogoUrl + '\'' +
                ", author='" + author + '\'' +
                ", sentences=" + sentences +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
