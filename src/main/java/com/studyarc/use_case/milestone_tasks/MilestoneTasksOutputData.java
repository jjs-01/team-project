package com.studyarc.use_case.milestone_tasks;

import com.studyarc.entity.Milestone;

import java.util.List;

/**
 * Output data class for the saving milestones use case
 */
public class MilestoneTasksOutputData {

    private final int milestonesSaved;
    private final String planName;

    public MilestoneTasksOutputData(int milestonesSaved, String planName) {
        this.milestonesSaved = milestonesSaved;
        this.planName = planName;
    }

    public String getPlanName() { return planName; }

    public int milestonesSaved() { return milestonesSaved; }
}
