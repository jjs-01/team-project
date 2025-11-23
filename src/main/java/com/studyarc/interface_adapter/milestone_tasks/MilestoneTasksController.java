package com.studyarc.interface_adapter.milestone_tasks;

import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputBoundary;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInputData;

import java.util.List;
import java.util.Map;

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
     * @param milestones map of the milestones that the user has created
     * @param milestonesToTasks map of the milestone names to the tasks they're associated with
     */
    public void execute(String studyPlanName, Map<String, String> milestones, Map<String, List<String[]>> milestonesToTasks) {
        final MilestoneTasksInputData milestoneInputData = new MilestoneTasksInputData(studyPlanName,
                milestones, milestonesToTasks);

        milestoneUseCaseInteractor.execute(milestoneInputData);
    }
}
