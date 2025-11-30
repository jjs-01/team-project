package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

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
        String username = loadMilestonesDataAccessObject.getCurrentUsername();
        StudyPlan studyPlan = loadMilestonesDataAccessObject.getPlan(username, loadMilestonesInputData.getStudyPlanName());

        final LoadMilestonesOutputData loadMilestonesOutputData =
                new LoadMilestonesOutputData(loadMilestonesInputData.getStudyPlanName(), studyPlan.getFocus(),
                studyPlan.getMilestones());

        loadPresenter.prepareSuccessView(loadMilestonesOutputData);
    }
}
