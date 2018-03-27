package com.azhan.news.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhan.news.Model.NewsApi;
import com.azhan.news.Network.ConnectionApi;
import com.azhan.news.Network.NewsInterface;
import com.azhan.news.R;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

public class DetailNews extends AppCompatActivity {

    TextView title, description, author, date, url, source;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news_layout);
        setTitle("Detail Berita");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initialVariable();
        showDetail();
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(url.getText().toString()));
                startActivity(browser);
            }
        });
    }

    private void showDetail() {
        Intent getData = getIntent();
        title.setText(getData.getStringExtra("title"));
        description.setText(getData.getStringExtra("description"));
        source.setText("Sumber : "+getData.getStringExtra("source"));
        date.setText(" "+getData.getStringExtra("date"));
        author.setText(" "+getData.getStringExtra("author"));
        url.setText(getData.getStringExtra("url"));
        Picasso.with(DetailNews.this).load(getData.getStringExtra("urlImage")).into(image);
    }

    private void initialVariable() {
        title = (TextView) findViewById(R.id.titledetailTV);
        date = (TextView) findViewById(R.id.datedetailTV);
        author = (TextView) findViewById(R.id.authordetailTV);
        description = (TextView) findViewById(R.id.descriptiondetailTV);
        url = (TextView) findViewById(R.id.urldetailTV);
        source = (TextView) findViewById(R.id.sourcedetailTV);
        image = (ImageView) findViewById(R.id.imagedetailIV);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_out_up);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_up);
    }
}