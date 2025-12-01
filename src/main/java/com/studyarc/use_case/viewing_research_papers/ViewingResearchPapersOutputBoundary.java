package com.studyarc.use_case.viewing_research_papers;

public interface ViewingResearchPapersOutputBoundary {
    void prepareSuccessView(ViewingResearchPapersOutputData outputData);
    void prepareFailView(String errorMessage);
}
