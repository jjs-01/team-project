package com.studyarc.use_case.milestone_tasks;

/**
 * Output data class for the saving milestones use case
 */
public class MilestoneTasksOutputData {

    private final String firstMilestone;

    public MilestoneTasksOutputData(String firstMilestone) {this.firstMilestone = firstMilestone;}

    public String getFirstMilestone() { return firstMilestone; }
}
