package com.studyarc.use_case.viewing_research_papers;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;

public class ViewingResearchPapersInteractor implements ViewingResearchPapersInputBoundary {
    private ViewingResearchPapersDataAccessInterface repository;
    private ViewingResearchPapersOutputBoundary presenter;

    public ViewingResearchPapersInteractor(ViewingResearchPapersDataAccessInterface repository,
                                           ViewingResearchPapersOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewingResearchPapersInputData inputData) {
        try {
            ArrayList<StudyPlan> plans = repository.getPlans();
            boolean hasPlans = !plans.isEmpty();
            ViewingResearchPapersOutputData outputData =
                    new ViewingResearchPapersOutputData(plans, hasPlans);
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}