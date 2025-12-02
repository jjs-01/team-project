package com.studyarc.use_case.add_papers_to_plan;

public class AddPapersToPlanInputData {
    private final String planName;
    private final String searchQuery;
    private final int limit;

    public AddPapersToPlanInputData(String planName, String searchQuery, int limit) {
        this.planName = planName;
        this.searchQuery = searchQuery;
        this.limit = limit;
    }

    public String getPlanName() {
        return planName;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public int getLimit() {
        return limit;
    }
}