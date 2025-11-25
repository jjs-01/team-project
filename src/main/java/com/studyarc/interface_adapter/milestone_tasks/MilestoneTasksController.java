package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputData;

import java.util.List;

/**
 * Controller for the MilestoneTasks Use Case
 */
public class MilestoneTasksController {
    private final MilestoneTasksInputBoundary milestoneUseCaseInteractor;

    public MilestoneTasksController(MilestoneTasksInputBoundary milestoneUseCaseInteractor) {
        this.milestoneUseCaseInteractor = milestoneUseCaseInteractor;
    }

    /**
     * Executes the MilestoneTasks Use Case
     * @param milestoneIndexToTasks map of the milestones that the user has created
     * @param milestoneNames map of the milestone names to the tasks they're associated with
     * @param milestoneDates map of the milestone names to the tasks they're associated with
     */
    public void execute(String studyPlanName, List<List<String[]>> milestoneIndexToTasks,
                        List<String> milestoneNames, List<String> milestoneDates) {
        final MilestoneTasksInputData milestoneInputData = new MilestoneTasksInputData(studyPlanName,
                milestoneNames, milestoneDates, milestoneIndexToTasks);

        milestoneUseCaseInteractor.execute(milestoneInputData);
    }
}
