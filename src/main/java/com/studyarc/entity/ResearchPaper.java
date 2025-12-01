package com.studyarc.entity;

import java.util.List;

public class ResearchPaper {
    private final String id;
    private final String title;
    private final List<String> authors;
    private final String abstractText;
    private final String url;
    private final String doi;
    private final Integer year;
    private final String downloadUrl;

    public ResearchPaper(String id,
                         String title,
                         List<String> authors,
                         String abstractText,
                         String url,
                         String doi,
                         Integer year,
                         String downloadUrl) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.abstractText = abstractText;
        this.url = url;
        this.doi = doi;
        this.year = year;
        this.downloadUrl = downloadUrl;
    }


    public String getId() { return id; }
    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public String getAbstractText() { return abstractText; }
    public String getUrl() { return url; }
    public String getDoi() { return doi; }
    public Integer getYear() { return year; }
    public String getDownloadUrl() { return downloadUrl; }


    public String getAuthorsAsString() {
        if (authors == null || authors.isEmpty()) {
            return "Unknown";
        }
        return String.join(", ", authors);
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", year=" + year +
                ", doi='" + doi + '\'' +
                '}';
    }
}