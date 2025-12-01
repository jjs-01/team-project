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
            System.out.println("ViewingResearchPapersInteractor.execute() called");

            // Get plans for current user
            ArrayList<StudyPlan> plans = repository.getPlans();

            System.out.println("Loaded " + plans.size() + " plans from database");
            for (StudyPlan plan : plans) {
                System.out.println("  - Plan: " + plan.getTitle() + " has " +
                        plan.getResearchPapers().size() + " papers");
            }

            boolean hasPlans = !plans.isEmpty();

            ViewingResearchPapersOutputData outputData =
                    new ViewingResearchPapersOutputData(plans, hasPlans);

            presenter.prepareSuccessView(outputData);
            System.out.println("Presenter.prepareSuccessView() called");

        } catch (Exception e) {
            System.err.println("Error in ViewingResearchPapersInteractor: " + e.getMessage());
            e.printStackTrace();
            presenter.prepareFailView("Failed to load research papers: " + e.getMessage());
        }
    }
}