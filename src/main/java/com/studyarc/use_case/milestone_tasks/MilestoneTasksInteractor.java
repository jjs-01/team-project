package com.studyarc.use_case.milestone_tasks;

public class MilestoneTasksInteractor implements MilestoneTasksInputBoundary {
    private final MilestoneTasksDataAccessInterface milestoneDataAccessObject;
    private final MilestoneTasksOutputBoundary milestonePresenter;

    public MilestoneTasksInteractor (MilestoneTasksDataAccessInterface milestoneDataAccessObject,
                                     MilestoneTasksOutputBoundary milestonePresenter) {
        this.milestoneDataAccessObject = milestoneDataAccessObject;
        this.milestonePresenter = milestonePresenter;
    }

    @Override
    public void execute(MilestoneTasksInputData milestoneInputData) {
        System.out.println("Working on Interactor Execution");
    }
}
