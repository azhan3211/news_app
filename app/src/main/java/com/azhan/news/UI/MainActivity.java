package com.azhan.news.UI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.azhan.news.Adapter.NewsAdapter;
import com.azhan.news.Model.NewsApi;
import com.azhan.news.Model.NewsData;
import com.azhan.news.Network.ConnectionApi;
import com.azhan.news.Network.NewsInterface;
import com.azhan.news.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView newsRecyclerView;
    RecyclerView.Adapter newsAdapter;
    List<NewsData> newsDatas;
    LinearLayout reload;
    SwipeRefreshLayout refreshNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Berita");
        initialVariable();
        refresh(connectionCheck());
        refreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(connectionCheck());
            }
        });
    }

    private void refresh(Boolean check){
        if (check){
            reload.setVisibility(View.GONE);
            refreshNews.setVisibility(View.VISIBLE);
            showNews();
        } else{
            reload.setVisibility(View.VISIBLE);
            refreshNews.setVisibility(View.GONE);
            refreshNews.setRefreshing(false);
            Toast.makeText(MainActivity.this, "Tidak Ada Koneksi ke Internet", Toast.LENGTH_SHORT).show();
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refresh(connectionCheck());
                }
            });
        }
    }

    private void showNews() {
        ConnectionApi connectionApi = new ConnectionApi();
        Retrofit retrofit = connectionApi.service();
        NewsInterface newsInterface = retrofit.create(NewsInterface.class);
        newsDatas = new ArrayList<>();
        String country = "id";
        String category = "business";
        String apiKey = "d9ed37cef7f84f26841e0d7fb6079ebb";
        Call<NewsApi> call = newsInterface.getNews(country, category, apiKey);
        call.enqueue(new Callback<NewsApi>() {
            @Override
            public void onResponse(Call<NewsApi> call, Response<NewsApi> response) {
                NewsApi result = response.body();
                for(int i = 0; i < result.getArticles().size(); i++){
                    NewsData newsData = new NewsData(
                            result.getArticles().get(i).getTitle(),
                            result.getArticles().get(i).getAuthor(),
                            result.getArticles().get(i).getPublishedAt(),
                            result.getArticles().get(i).getDescription(),
                            result.getArticles().get(i).getUrlToImage(),
                            result.getArticles().get(i).getSource().getName(),
                            result.getArticles().get(i).getUrl()
                    );
                    newsDatas.add(newsData);
                }
                newsAdapter = new NewsAdapter(MainActivity.this, newsDatas);
                newsRecyclerView.setAdapter(newsAdapter);
                refreshNews.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewsApi> call, Throwable t) {

            }
        });
    }

    private void initialVariable() {
        newsRecyclerView = (RecyclerView) findViewById(R.id.newsRV);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL, false));
        reload = (LinearLayout) findViewById(R.id.reloadLL);
        refreshNews = (SwipeRefreshLayout) findViewById(R.id.refreshNews);
    }

    private Boolean connectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }
}
