package me.vinitagrawal.bullets.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Story {

    @SerializedName("stories")
    List<Article> articleList;
}
