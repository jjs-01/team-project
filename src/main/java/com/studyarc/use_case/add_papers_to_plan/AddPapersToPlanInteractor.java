package com.studyarc.use_case.add_papers_to_plan;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.search_research_papers.SearchResearchPapersDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class AddPapersToPlanInteractor implements AddPapersToPlanInputBoundary {
    private final AddPapersToPlanDataAccessInterface dataAccess;
    private final AddPapersToPlanOutputBoundary presenter;

    public AddPapersToPlanInteractor(AddPapersToPlanDataAccessInterface dataAccess,
                                     AddPapersToPlanOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddPapersToPlanInputData inputData) {
        try {
            // Get the plan
            StudyPlan plan = dataAccess.getPlan(inputData.getPlanName());

            if (plan == null) {
                presenter.prepareFailView("Plan not found: " + inputData.getPlanName());
                return;
            }

            // Search for papers using CORE API
            SearchResearchPapersDataAccessInterface.SearchResult searchResult =
                    dataAccess.searchPapers(inputData.getSearchQuery(), inputData.getLimit(), 0);

            List<ResearchPaper> newPapers = searchResult.getPapers();

            if (newPapers.isEmpty()) {
                presenter.prepareFailView("No papers found for query: " + inputData.getSearchQuery());
                return;
            }

            // Add papers to the plan (avoid duplicates by checking paper ID)
            List<ResearchPaper> addedPapers = new ArrayList<>();
            for (ResearchPaper paper : newPapers) {
                if (!planContainsPaper(plan, paper)) {
                    plan.addResearchPaper(paper);
                    addedPapers.add(paper);
                }
            }

            if (addedPapers.isEmpty()) {
                presenter.prepareFailView("All found papers were already in the plan.");
                return;
            }

            // Save the updated plan
            dataAccess.savePlan(plan);

            // Prepare success response
            AddPapersToPlanOutputData outputData = new AddPapersToPlanOutputData(
                    plan.getTitle(),
                    addedPapers,
                    true
            );
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            presenter.prepareFailView("Failed to add papers: " + e.getMessage());
        }
    }

    /**
     * Check if a plan already contains a paper (by ID)
     */
    private boolean planContainsPaper(StudyPlan plan, ResearchPaper newPaper) {
        if (newPaper.getId() == null) {
            return false;
        }

        for (ResearchPaper existingPaper : plan.getResearchPapers()) {
            if (newPaper.getId().equals(existingPaper.getId())) {
                return true;
            }
        }
        return false;
    }
}