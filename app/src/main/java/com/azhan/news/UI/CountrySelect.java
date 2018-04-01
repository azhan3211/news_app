package com.azhan.news.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.azhan.news.Adapter.CountrySelectAdapter;
import com.azhan.news.Model.NewsAssets;
import com.azhan.news.R;

import java.util.ArrayList;
import java.util.List;

public class CountrySelect extends AppCompatActivity {

    RecyclerView countrySelect;
    RecyclerView.Adapter countrySelectAdapter;
    List<String> countries;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_select_layout);
        setTitle("Select Country");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initialVarible();
        countrySetup();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        return true;
    }




    private void initialVarible() {
        countrySelect = (RecyclerView) findViewById(R.id.countrySelectRV);
        countrySelect.setLayoutManager(new LinearLayoutManager(CountrySelect.this, LinearLayoutManager.VERTICAL, false));
        countries = new ArrayList<>();
    }

    private void countrySetup(){
        countries.addAll(NewsAssets.getCountry());
        countrySelectAdapter = new CountrySelectAdapter(CountrySelect.this, countries);
        countrySelect.setAdapter(countrySelectAdapter);

    }
}