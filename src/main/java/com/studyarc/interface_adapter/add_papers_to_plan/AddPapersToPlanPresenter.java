package com.studyarc.interface_adapter.add_papers_to_plan;

import com.studyarc.interface_adapter.viewing_research_papers.ViewingResearchPapersViewModel;
import com.studyarc.use_case.add_papers_to_plan.AddPapersToPlanOutputBoundary;
import com.studyarc.use_case.add_papers_to_plan.AddPapersToPlanOutputData;

public class AddPapersToPlanPresenter implements AddPapersToPlanOutputBoundary {
    private final ViewingResearchPapersViewModel viewModel;

    public AddPapersToPlanPresenter(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddPapersToPlanOutputData outputData) {
        String message = String.format("Added %d paper(s) to '%s'",
                outputData.getPaperCount(),
                outputData.getPlanName());
        viewModel.setSuccessMessage(message);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setErrorMessage(errorMessage);
    }
}