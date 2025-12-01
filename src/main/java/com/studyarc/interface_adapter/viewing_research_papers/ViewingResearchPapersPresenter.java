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
        System.out.println("ViewingResearchPapersPresenter.prepareSuccessView() called");

        // Pass the StudyPlan entities directly to the ViewModel
        List<StudyPlan> plans = outputData.getPlans();

        System.out.println("Setting " + plans.size() + " plans in ViewModel");
        for (StudyPlan plan : plans) {
            System.out.println("  - Plan: " + plan.getTitle() + " with " +
                    plan.getResearchPapers().size() + " papers");
        }

        viewModel.setStudyPlans(plans);
        viewModel.setHasPlans(outputData.hasPlans());

        System.out.println("ViewModel updated successfully");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("ViewingResearchPapersPresenter.prepareFailView: " + errorMessage);
        viewModel.setErrorMessage("Unable to load research papers. Please try again later.");
        viewModel.setStudyPlans(new ArrayList<>());
        viewModel.setHasPlans(false);
    }
}