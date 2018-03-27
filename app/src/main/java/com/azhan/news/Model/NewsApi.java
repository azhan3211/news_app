package com.azhan.news.Model;

import java.util.List;

public class NewsApi {
    private List<Articles> articles;
    public List<Articles> getArticles(){return articles;}
    public class Articles {
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        private Source source;
        public Source getSource() { return source;}
        public class Source{
            private String id, name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }

}