package com.studyarc.use_case.viewing_research_papers;

import com.studyarc.entity.StudyPlan;

import java.util.List;

public class ViewingResearchPapersInteractor implements ViewingResearchPapersInputBoundary {
    private final ViewingResearchPapersDataAccessInterface repository;
    private final ViewingResearchPapersOutputBoundary presenter;

    public ViewingResearchPapersInteractor(ViewingResearchPapersDataAccessInterface repository,
                                           ViewingResearchPapersOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        try {
            List<StudyPlan> plans = repository.getPlans();
            boolean hasPlans = !plans.isEmpty();
            ViewingResearchPapersOutputData outputData =
                    new ViewingResearchPapersOutputData(plans, hasPlans);
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}