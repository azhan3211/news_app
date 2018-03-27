package com.azhan.news.Model;

public class NewsData {
    private String title, author, date, description, image, source, url;

    public NewsData(String title, String author, String date, String description, String image, String source, String url) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.image = image;
        this.source = source;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }
}