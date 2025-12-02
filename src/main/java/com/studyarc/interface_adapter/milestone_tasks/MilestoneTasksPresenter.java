package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputData;

/**
 * Presenter for the saving milestones use case
 */
public class MilestoneTasksPresenter implements MilestoneTasksOutputBoundary {

    private final MilestoneTasksViewModel milestoneTasksViewModel;

    public MilestoneTasksPresenter(MilestoneTasksViewModel milestoneTasksViewModel) {
        this.milestoneTasksViewModel = milestoneTasksViewModel;
    }

    @Override
    public void prepareSuccessView(MilestoneTasksOutputData response) {
        final MilestoneTasksState saveState = milestoneTasksViewModel.getState();
        saveState.setMilestoneSaveMessage("Saved " + response.getMilestonesSaved()
                + " milestones for " + response.getPlanName() + "!");
        milestoneTasksViewModel.firePropertyChange("saved plan");
    }

    @Override
    public void prepareFailView(String error) {
        final MilestoneTasksState saveState = milestoneTasksViewModel.getState();
        saveState.setMilestoneSaveError(error);
        milestoneTasksViewModel.firePropertyChange("failed to save");
    }
}
