package com.studyarc.use_case.milestone_tasks;

import com.studyarc.entity.StudyPlan;

/**
 * DAO interface for the MilestoneTasks Case.
 */
public interface MilestoneTasksDataAccessInterface {
    /**
     * Saves the milestones and tasks to the User's StudyPlan
     */
    void savePlan(String username, StudyPlan plan);

    StudyPlan getPlan(String planName);

    String getCurrentUsername();
}
