package com.studyarc.entity;

public class ResearchPaper {
    private final String id;
    private final String title;
    private final String authors;
    private final String abstractText;
    private final String url;

    public ResearchPaper(String id,
                         String title,
                         String authors,
                         String abstractText,
                         String url) {

        this.id = id;
        this.title = title;
        this.authors = authors;
        this.abstractText = abstractText;
        this.url = url;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthors() { return authors; }
    public String getAbstractText() { return abstractText; }
    public String getUrl() { return url; }
}
