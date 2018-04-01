package com.azhan.news.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhan.news.R;
import com.azhan.news.UI.MainActivity;

import java.util.List;

public class CountrySelectAdapter extends RecyclerView.Adapter<CountrySelectAdapter.ViewHolder> {

    List<String> countries;
    Activity activity;

    public CountrySelectAdapter(Activity activity, List<String> country) {
        this.activity = activity;
        this.countries = country;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String[] countryNC = countries.get(position).split("-");
        holder.countryCode.setText(countryNC[1].toUpperCase());
        holder.countryName.setText(countryNC[0]);
        holder.countryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, countryNC[1]+" - "+countryNC[0], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("country", countryNC[1]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.setResult(activity.RESULT_OK, intent);
                activity.finish();
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView countryCode, countryName;
        private LinearLayout countryContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            countryCode = (TextView) itemView.findViewById(R.id.countryCodeTV);
            countryName = (TextView) itemView.findViewById(R.id.countryNameTV);
            countryContainer = (LinearLayout) itemView.findViewById(R.id.countryContainerLL);
        }
    }
}