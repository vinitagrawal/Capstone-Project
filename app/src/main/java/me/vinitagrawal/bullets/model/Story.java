package me.vinitagrawal.bullets.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Story {

    @SerializedName("stories")
    List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    @Override
    public String toString() {
        return "Story{" +
                "articleList=" + articleList +
                '}';
    }
}
