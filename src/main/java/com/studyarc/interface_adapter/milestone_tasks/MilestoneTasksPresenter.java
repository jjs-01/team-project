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
        // idk what to do here tbh
        System.out.println("Saved for " + response.getUsername());
    }

    @Override
    public void prepareFailView(String error) {
        System.out.println(error + "Couldn't save");
    }
}
