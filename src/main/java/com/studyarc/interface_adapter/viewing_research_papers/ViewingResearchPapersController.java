package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInputBoundary;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInputData;

public class ViewingResearchPapersController {
    private ViewingResearchPapersInputBoundary inputBoundary;
    private String currentUsername;

    public ViewingResearchPapersController(ViewingResearchPapersInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }


    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public void handleViewingResearchPapers() {
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();
        inputBoundary.execute(inputData);
    }
}