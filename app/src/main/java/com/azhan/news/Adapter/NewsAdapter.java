package com.azhan.news.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhan.news.Model.NewsData;
import com.azhan.news.R;
import com.azhan.news.UI.DetailNews;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<NewsData> news;
    Activity activity;
    String[] months = {"Jan","Feb","Mar","Apr","Mei","Jun","Jul","Agt","Spt","Okt","Nov","Des"};
    public NewsAdapter(Activity activity, List<NewsData> news) {
        this.activity = activity;
        this.news = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsData item = news.get(position);
        holder.title.setText(item.getTitle());
        holder.source.setText(item.getSource());
        String[] splitDate = item.getDate().split("-");
        final String tanggal = splitDate[2].substring(0,2);
        final String bulan = months[Integer.parseInt(splitDate[1])-1];
        final String tahun = splitDate[0];
        holder.date.setText(" "+tanggal+" "+bulan+" "+tahun);
        holder.description.setText(item.getDescription());
        if(item.getImage() != null)
            Picasso.with(activity).load(item.getImage()).error(R.drawable.ic_launcher_background).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.ic_launcher_background);
        holder.newsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailNews.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("author", item.getAuthor());
                intent.putExtra("date", tanggal+" "+bulan+" "+tahun);
                intent.putExtra("description", item.getDescription());
                intent.putExtra("url", item.getUrl());
                intent.putExtra("urlImage", item.getImage());
                intent.putExtra("source", item.getSource());
                activity.startActivity(intent);
                activity.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, date, description, source;
        private ImageView image;
        private LinearLayout newsLL;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            source = (TextView) itemView.findViewById(R.id.sourceTV);
            date = (TextView) itemView.findViewById(R.id.dateTV);
            description = (TextView) itemView.findViewById(R.id.descriptionTV);
            image = (ImageView) itemView.findViewById(R.id.imageIV);
            newsLL = (LinearLayout) itemView.findViewById(R.id.newsLL);
        }
    }
}