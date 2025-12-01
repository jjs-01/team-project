package com.studyarc.interface_adapter.search_research_papers;

import com.studyarc.use_case.search_research_papers.SearchResearchPapersDataAccessInterface;
import com.studyarc.data_access.core.COREAPIClient;
import com.studyarc.entity.ResearchPaper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter that connects the CORE API client to your application's data access interface.
 * This translates between the external API format and your domain entities.
 */
public class CoreResearchAdapter implements SearchResearchPapersDataAccessInterface {
    private final COREAPIClient apiClient;

    public CoreResearchAdapter(COREAPIClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public SearchResult searchPapers(String query, int limit, int offset) {
        try {
            COREAPIClient.SearchResult apiResult = apiClient.searchPapers(query, limit, offset);

            List<ResearchPaper> domainPapers = new ArrayList<>();
            for (COREAPIClient.Paper apiPaper : apiResult.getPapers()) {
                domainPapers.add(toDomainModel(apiPaper));
            }

            return new SearchResult(apiResult.getTotalHits(), domainPapers);

        } catch (IOException e) {
            throw new RuntimeException("Failed to search papers: " + e.getMessage(), e);
        }
    }

    @Override
    public SearchResult searchPapersByYear(String query, int yearFrom, int yearTo, int limit) {
        try {
            COREAPIClient.SearchQuery searchQuery = new COREAPIClient.SearchQuery(query);
            searchQuery.setLimit(limit);
            searchQuery.setYearFrom(yearFrom);
            searchQuery.setYearTo(yearTo);

            COREAPIClient.SearchResult apiResult = apiClient.advancedSearch(searchQuery);

            List<ResearchPaper> domainPapers = new ArrayList<>();
            for (COREAPIClient.Paper apiPaper : apiResult.getPapers()) {
                domainPapers.add(toDomainModel(apiPaper));
            }

            return new SearchResult(apiResult.getTotalHits(), domainPapers);

        } catch (IOException e) {
            throw new RuntimeException("Failed to search papers by year: " + e.getMessage(), e);
        }
    }

    @Override
    public ResearchPaper getPaperById(String paperId) {
        try {
            COREAPIClient.Paper apiPaper = apiClient.getPaperById(paperId);
            return toDomainModel(apiPaper);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get paper by ID: " + e.getMessage(), e);
        }
    }

    /**
     * Convert CORE API Paper to your domain ResearchPaper entity
     */
    private ResearchPaper toDomainModel(COREAPIClient.Paper apiPaper) {
        // Get authors list, or empty list if null
        List<String> authors = apiPaper.getAuthors();
        if (authors == null) {
            authors = new ArrayList<>();
        }

        // Get abstract, or empty string if null
        String abstractText = apiPaper.getAbstract();
        if (abstractText == null) {
            abstractText = "";
        }

        // Get URL, fallback to constructing from ID if needed
        String url = apiPaper.getUrl();
        if (url == null && apiPaper.getId() != null) {
            url = "https://core.ac.uk/works/" + apiPaper.getId();
        }

        return new ResearchPaper(
                apiPaper.getId(),
                apiPaper.getTitle(),
                authors,
                abstractText,
                url,
                apiPaper.getDoi(),
                apiPaper.getYear(),
                apiPaper.getDownloadUrl()
        );
    }
}