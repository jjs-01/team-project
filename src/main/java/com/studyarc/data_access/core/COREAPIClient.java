package com.studyarc.data_access.core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client for interacting with the CORE API (COnnecting REpositories)
 * API Documentation: https://api.core.ac.uk/docs/v3
 */
public class COREAPIClient {
    private final OkHttpClient client;
    private final Gson gson;
    private final String apiKey;
    private static final String BASE_URL = "https://api.core.ac.uk/v3";

    public COREAPIClient(String apiKey) {
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.apiKey = apiKey;
    }

    /**
     * Search for research papers
     * @param query Search term
     * @param limit Maximum number of results (1-100)
     * @param offset Pagination offset
     * @return Search results with papers and total count
     */
    public SearchResult searchPapers(String query, int limit, int offset) throws IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("q", query);
        requestBody.addProperty("limit", limit);
        requestBody.addProperty("offset", offset);

        String url = BASE_URL + "/search/works";

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed: " + response.code() + " - " + response.message());
            }

            String jsonData = response.body().string();
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);

            return parseSearchResult(jsonObject);
        }
    }

    /**
     * Get a specific paper by its CORE ID
     * @param coreId CORE paper identifier
     * @return Paper details
     */
    public Paper getPaperById(String coreId) throws IOException {
        String url = BASE_URL + "/works/" + coreId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed: " + response.code());
            }

            String jsonData = response.body().string();
            JsonObject paperJson = gson.fromJson(jsonData, JsonObject.class);
            return parsePaper(paperJson);
        }
    }

    /**
     * Advanced search with filters (year range, etc.)
     * @param searchQuery Search query with filters
     * @return Search results
     */
    public SearchResult advancedSearch(SearchQuery searchQuery) throws IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("q", searchQuery.getQuery());
        requestBody.addProperty("limit", searchQuery.getLimit());
        requestBody.addProperty("offset", searchQuery.getOffset());

        if (searchQuery.getYearFrom() != null) {
            requestBody.addProperty("yearFrom", searchQuery.getYearFrom());
        }
        if (searchQuery.getYearTo() != null) {
            requestBody.addProperty("yearTo", searchQuery.getYearTo());
        }

        String url = BASE_URL + "/search/works";

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed: " + response.code());
            }

            String jsonData = response.body().string();
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            return parseSearchResult(jsonObject);
        }
    }

    /**
     * Parse search result JSON into SearchResult object
     */
    private SearchResult parseSearchResult(JsonObject jsonObject) {
        int totalHits = jsonObject.has("totalHits") ? jsonObject.get("totalHits").getAsInt() : 0;
        List<Paper> papers = new ArrayList<>();

        if (jsonObject.has("results")) {
            JsonArray results = jsonObject.getAsJsonArray("results");
            for (int i = 0; i < results.size(); i++) {
                papers.add(parsePaper(results.get(i).getAsJsonObject()));
            }
        }

        return new SearchResult(totalHits, papers);
    }

    /**
     * Safely extract a string from JsonObject, returning null if field doesn't exist or is null
     */
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }

    /**
     * Safely extract an integer from JsonObject, returning null if field doesn't exist or is null
     */
    private Integer getIntOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsInt();
        }
        return null;
    }
    private Paper parsePaper(JsonObject json) {
        Paper paper = new Paper();

        paper.setId(getStringOrNull(json, "id"));
        paper.setTitle(getStringOrNull(json, "title"));
        paper.setAbstract(getStringOrNull(json, "abstract"));
        paper.setDownloadUrl(getStringOrNull(json, "downloadUrl"));
        paper.setDoi(getStringOrNull(json, "doi"));
        paper.setYear(getIntOrNull(json, "yearPublished"));

        // Parse authors array
        if (json.has("authors") && !json.get("authors").isJsonNull()) {
            JsonArray authorsArray = json.getAsJsonArray("authors");
            List<String> authors = new ArrayList<>();
            for (int i = 0; i < authorsArray.size(); i++) {
                JsonObject authorObj = authorsArray.get(i).getAsJsonObject();
                String name = getStringOrNull(authorObj, "name");
                if (name != null) {
                    authors.add(name);
                }
            }
            paper.setAuthors(authors);
        }

        // Set URL
        String downloadUrl = getStringOrNull(json, "downloadUrl");
        if (downloadUrl != null) {
            paper.setUrl(downloadUrl);
        } else {
            String id = getStringOrNull(json, "id");
            if (id != null) {
                paper.setUrl("https://core.ac.uk/works/" + id);
            }
        }

        return paper;
    }


    /**
     * Represents a research paper from CORE API
     */
    public static class Paper {
        private String id;
        private String title;
        private String abstractText;
        private String downloadUrl;
        private String doi;
        private Integer year;
        private List<String> authors;
        private String url;

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getAbstract() { return abstractText; }
        public void setAbstract(String abstractText) { this.abstractText = abstractText; }

        public String getDownloadUrl() { return downloadUrl; }
        public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

        public String getDoi() { return doi; }
        public void setDoi(String doi) { this.doi = doi; }

        public Integer getYear() { return year; }
        public void setYear(Integer year) { this.year = year; }

        public List<String> getAuthors() { return authors; }
        public void setAuthors(List<String> authors) { this.authors = authors; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    /**
     * Container for search results
     */
    public static class SearchResult {
        private final int totalHits;
        private final List<Paper> papers;

        public SearchResult(int totalHits, List<Paper> papers) {
            this.totalHits = totalHits;
            this.papers = papers;
        }

        public int getTotalHits() { return totalHits; }
        public List<Paper> getPapers() { return papers; }
    }

    /**
     * Query object for advanced search
     */
    public static class SearchQuery {
        private String query;
        private int limit = 10;
        private int offset = 0;
        private Integer yearFrom;
        private Integer yearTo;

        public SearchQuery(String query) {
            this.query = query;
        }

        // Getters and setters
        public String getQuery() { return query; }
        public void setQuery(String query) { this.query = query; }

        public int getLimit() { return limit; }
        public void setLimit(int limit) { this.limit = limit; }

        public int getOffset() { return offset; }
        public void setOffset(int offset) { this.offset = offset; }

        public Integer getYearFrom() { return yearFrom; }
        public void setYearFrom(Integer yearFrom) { this.yearFrom = yearFrom; }

        public Integer getYearTo() { return yearTo; }
        public void setYearTo(Integer yearTo) { this.yearTo = yearTo; }
    }
}