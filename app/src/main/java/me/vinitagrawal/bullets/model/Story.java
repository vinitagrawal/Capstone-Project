package me.vinitagrawal.bullets.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Story {

    @SerializedName("stories")
    ArrayList<Article> articleList;

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    @Override
    public String toString() {
        return "Story{" +
                "articleList=" + articleList +
                '}';
    }
}
