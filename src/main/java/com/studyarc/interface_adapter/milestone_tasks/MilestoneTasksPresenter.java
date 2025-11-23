package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksOutputData;
import com.studyarc.view.MilestoneTasksView;

public class MilestoneTasksPresenter implements MilestoneTasksOutputBoundary {

    private final MilestoneTasksView milestoneTasksView;

    public MilestoneTasksPresenter(MilestoneTasksView milestoneTasksView) {
        this.milestoneTasksView = milestoneTasksView;
    }

    @Override
    public void prepareSuccessView(MilestoneTasksOutputData outputData) {

    }
}
