package com.studyarc.interface_adapter.viewing_research_papers;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData;

import java.util.ArrayList;
import java.util.List;

public class ViewingResearchPapersPresenter implements ViewingResearchPapersOutputBoundary {
    private final ViewingResearchPapersViewModel viewModel;

    public ViewingResearchPapersPresenter(ViewingResearchPapersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewingResearchPapersOutputData outputData) {
        // Pass the StudyPlan entities directly to the ViewModel
        List<StudyPlan> plans = outputData.getPlans();

        viewModel.setStudyPlans(plans);
        viewModel.setHasPlans(outputData.hasPlans());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Error loading papers: " + errorMessage);
        viewModel.setErrorMessage("Unable to load research papers. Please try again later.");
        viewModel.setStudyPlans(new ArrayList<>());
        viewModel.setHasPlans(false);
    }
}