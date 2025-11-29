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
     * Returns the user's tasks from a given milestone and plan
     * @return an ArrayList of Task Objects
     */
    ArrayList<Task> getTasksForMilestone(User user, StudyPlan plan, Milestone milestone);

    /**
     * Returns the user's tasks from a given plan
     * @return an ArrayList of Task Objects
     */
    ArrayList<Milestone> getMilestones(User user, StudyPlan plan);

    /**
     * Saves the milestones and tasks to the User's StudyPlan
     */
    void savePlan(User user, StudyPlan plan);

//    User getUser(String username);

    ArrayList<StudyPlan> getPlans(String user);

    StudyPlan getPlan(User user, String planName);
}
