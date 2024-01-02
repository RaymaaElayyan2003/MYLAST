package com.example.myb;

public class Book {
    private String title;
    private String authors;
    private String description;
    private String thumbnailUrl;//whichs for getting the like of the pic
    private String source;

    Book(){}

    public Book(String title, String authors) {
        this.title = title;
        this.authors = authors;
    }

    public Book(String title, String authors, String description, String thumbnailUrl, String source) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getSource() {
        return source;
    }
}
