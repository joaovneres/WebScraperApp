package com.scraper.model;

public class NewsArticle {
    private String url;
    private String title;
    private String subtitle;
    private String author;
    private String publicationDate;
    private String content;

    public NewsArticle(String url, String title, String subtitle, String author, String publicationDate, String content) {
        this.url = url;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.publicationDate = publicationDate;
        this.content = content;
    }

    public NewsArticle() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "URL: " + url + "\n" +
                "Title: " + title + "\n" +
                "Subtitle: " + subtitle + "\n" +
                "Author: " + author + "\n" +
                "Publication Date: " + publicationDate + "\n" +
                "Content: " + content + "\n";
    }
}
