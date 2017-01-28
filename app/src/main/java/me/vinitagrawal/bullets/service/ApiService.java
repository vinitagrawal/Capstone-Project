package me.vinitagrawal.bullets.service;

import java.util.Map;

import me.vinitagrawal.bullets.model.Story;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("v1/stories?published_at.start=NOW-30DAYS&published_at.end=NOW" +
            "&categories.taxonomy=iab-qag&language[]=en&per_page=25")
    Call<Story> getStories(@QueryMap Map<String,String> options);

}
