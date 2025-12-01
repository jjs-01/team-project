package com.studyarc.use_case.milestone_tasks;

/**
 * Input boundary interface for the saving milestones use case
 */
public interface MilestoneTasksInputBoundary {
    // executes the save milestone and tasks use case
    void execute(MilestoneTasksInputData milestoneTasksInputData);
}

