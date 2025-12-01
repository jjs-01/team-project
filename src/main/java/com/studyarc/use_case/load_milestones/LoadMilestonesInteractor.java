package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.StudyPlan;

/**
 * Interactor for the load milestones use case
 */
public class LoadMilestonesInteractor implements LoadMilestonesInputBoundary {
    private final LoadMilestonesDataAccessInterface loadMilestonesDataAccessObject;
    private final LoadMilestonesOutputBoundary loadPresenter;

    public LoadMilestonesInteractor(LoadMilestonesDataAccessInterface loadMilestonesDataAccessObject,
                                    LoadMilestonesOutputBoundary loadPresenter) {
        this.loadMilestonesDataAccessObject = loadMilestonesDataAccessObject;
        this.loadPresenter = loadPresenter;
    }

    @Override
    public void execute(LoadMilestonesInputData loadMilestonesInputData) {
        StudyPlan studyPlan = loadMilestonesDataAccessObject.getPlan(loadMilestonesInputData.getStudyPlanName());

        if (studyPlan == null) {
            loadPresenter.prepareFailView("Can't retrieve study plan");
            return;
        }

        final LoadMilestonesOutputData loadMilestonesOutputData =
                new LoadMilestonesOutputData(loadMilestonesInputData.getStudyPlanName(), studyPlan.getFocus(),
                studyPlan.getMilestones());

        loadPresenter.prepareSuccessView(loadMilestonesOutputData);
    }
}
