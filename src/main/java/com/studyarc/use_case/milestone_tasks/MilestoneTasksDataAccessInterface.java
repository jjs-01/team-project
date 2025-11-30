package com.studyarc.use_case.milestone_tasks;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.Milestone;
import com.studyarc.entity.User;

import java.util.ArrayList;

/**
 * DAO interface for the MilestoneTasks Case.
 */
public interface MilestoneTasksDataAccessInterface {
    /**
     * Saves the milestones and tasks to the User's StudyPlan
     */
    void savePlan(User user, StudyPlan plan);

    User getUser(String username);

    ArrayList<StudyPlan> getPlans(String user);

    StudyPlan getPlan(User user, String planName);
}
