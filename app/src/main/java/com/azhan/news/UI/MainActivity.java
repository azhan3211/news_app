package com.azhan.news.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.azhan.news.Adapter.NewsAdapter;
import com.azhan.news.Data.NewsPreferences;
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
    String country = "id";
    String category = "";
    String apiKey = "d9ed37cef7f84f26841e0d7fb6079ebb";
    NewsPreferences newsPreferences;
    private Menu menu;
    MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Berita");
        initialVariable();
        if(newsPreferences.getCategory(MainActivity.this) != null) {
            category = newsPreferences.getCategory(MainActivity.this);
            setTitle("Berita ("+category+")");
        }
        if(newsPreferences.getCountry(MainActivity.this) != null){
            country = newsPreferences.getCountry(MainActivity.this);
            refresh(connectionCheck(), category, country);
        } else
            refresh(connectionCheck(), category, country);
        refreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(connectionCheck(), category, country);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                country = data.getStringExtra("country");
                newsPreferences.saveCountry(MainActivity.this, country);
                refresh(connectionCheck(), category, country);
                menuItem = menu.findItem(R.id.countryNews);
                menuItem.setTitle(country);
            }
        }
    }

    private void refresh(Boolean check, final String category, final String country){
        if (check){
            reload.setVisibility(View.GONE);
            refreshNews.setVisibility(View.VISIBLE);
            showNews(category, country);
        } else{
            reload.setVisibility(View.VISIBLE);
            refreshNews.setVisibility(View.GONE);
            refreshNews.setRefreshing(false);
            Toast.makeText(MainActivity.this, "Tidak Ada Koneksi ke Internet", Toast.LENGTH_SHORT).show();
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refresh(connectionCheck(), category, country);
                }
            });
        }
        Log.d("kategorynya", category);
    }

    private void showRadioButtonCategoryNewsDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kategori_radio_button_layout);
        List<String> stringList=new ArrayList<>();  // here is list
        for(int i=0;i<5;i++) {
            stringList.add("RadioButton " + (i + 1));
        }
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.categoryRG);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.bisnisRB:
                        category = "business";
                        setTitle("Berita (Bisnis)");
                        break;
                    case R.id.hiburanRB:
                        category = "entertainment";
                        setTitle("Berita (Hiburan)");
                        break;
                    case R.id.kesehatanRB:
                        category = "health";
                        setTitle("Berita (Kesehatan)");
                        break;
                    case R.id.olahragaRB:
                        category = "sports";
                        setTitle("Berita (Olahraga)");
                        break;
                    case R.id.teknologiRB:
                        category = "technology";
                        setTitle("Berita (Teknologi)");
                        break;
                    case R.id.umumRB:
                        category = "general";
                        setTitle("Berita (Umum)");
                        break;
                    case R.id.sainsRB:
                        category = "science";
                        setTitle("Berita (Sains)");
                        break;
                    case R.id.semuaRB:
                        category = "";
                        setTitle("Berita");
                    default:
                        break;
                }
                newsPreferences.saveCategory(MainActivity.this, category);
                dialog.dismiss();
                refresh(connectionCheck(), category, country);
            }
        });
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        this.menu = menu;
        if(newsPreferences.getCountry(MainActivity.this) != null)
            menu.findItem(R.id.countryNews).setTitle(newsPreferences.getCountry(MainActivity.this));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.categoryNews) {
            // do something here
            showRadioButtonCategoryNewsDialog();
        }

        if (id == R.id.countryNews) {
//            showRadioButtonCountryNewsDialog();
            Intent intent = new Intent(MainActivity.this, CountrySelect.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNews(String category, String country) {
        ConnectionApi connectionApi = new ConnectionApi();
        Retrofit retrofit = connectionApi.service();
        NewsInterface newsInterface = retrofit.create(NewsInterface.class);
        newsDatas = new ArrayList<>();
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
        newsPreferences = new NewsPreferences();
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
