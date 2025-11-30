package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.interface_adapter.ViewManagerModel;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputData;

public class MilestoneTasksPresenter implements MilestoneTasksOutputBoundary {

    private final MilestoneTasksViewModel milestoneTasksViewModel;
    private final ViewManagerModel viewManagerModel;

    public MilestoneTasksPresenter(ViewManagerModel viewManagerModel,
                                   MilestoneTasksViewModel milestoneTasksViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.milestoneTasksViewModel = milestoneTasksViewModel;
    }

    @Override
    public void prepareSuccessView(MilestoneTasksOutputData response) {
        System.out.println(response.getFirstMilestone());
        milestoneTasksViewModel.firePropertyChange("saved plan");
    }

    @Override
    public void prepareFailView(String error) {
        final MilestoneTasksState saveState = milestoneTasksViewModel.getState();
        saveState.setMilestoneSaveError(error);
        milestoneTasksViewModel.firePropertyChange("failed to save");
    }
}
