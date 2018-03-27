package com.azhan.news.Network;

import com.azhan.news.Model.NewsApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {

    @GET("top-headlines")
    Call<NewsApi> getNews(@Query("country") String country,
                          @Query("category") String category,
                          @Query("apiKey") String apiKey);

}