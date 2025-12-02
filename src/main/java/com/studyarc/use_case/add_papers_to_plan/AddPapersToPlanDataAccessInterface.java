package com.studyarc.use_case.add_papers_to_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.search_research_papers.SearchResearchPapersDataAccessInterface;

public interface AddPapersToPlanDataAccessInterface {
    /**
     * Get a plan by name
     */
    StudyPlan getPlan(String planName);

    /**
     * Save changes to a plan
     */
    void savePlan(StudyPlan plan);

    /**
     * Search for research papers
     */
    SearchResearchPapersDataAccessInterface.SearchResult searchPapers(String query, int limit, int offset);
}