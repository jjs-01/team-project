package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.Task;
import com.studyarc.use_case.job_postings.JobPostingsInputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputData;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksController {
    private final MilestoneTasksInputBoundary milestoneTasksUseCaseInteractor;

    public MilestoneTasksController(MilestoneTasksInputBoundary milestoneTasksUseCaseInteractor) {
        this.milestoneTasksUseCaseInteractor = milestoneTasksUseCaseInteractor;
    }

    public void execute() {
        System.out.println("Execute not implemented yet.");
    }
}
