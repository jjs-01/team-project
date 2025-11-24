package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData;

import java.util.ArrayList;

public class ViewingResearchPapersPresenter implements ViewingResearchPapersOutputBoundary {
    private final ViewingResearchPapersViewModel viewModel;

    public ViewingResearchPapersPresenter(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewingResearchPapersOutputData outputData) {
        viewModel.setResearchPapers(outputData.getPapers());
        viewModel.setHasPapers(outputData.hasPapers());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Error loading papers: " + errorMessage);
        viewModel.setResearchPapers(new ArrayList<>());
        viewModel.setHasPapers(false);
    }
}