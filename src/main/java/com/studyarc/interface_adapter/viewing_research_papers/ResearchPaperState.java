package com.studyarc.interface_adapter.viewing_research_papers;

public class ResearchPaperState {
    private final String id;
    private final String title;
    private final String authors;
    private final String abstractText;
    private final String url;

    public ResearchPaperState(String id, String title, String authors,
                              String abstractText, String url) {
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
