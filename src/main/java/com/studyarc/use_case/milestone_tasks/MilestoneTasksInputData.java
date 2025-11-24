package com.studyarc.use_case.milestone_tasks;

import java.util.List;
import java.util.Map;

public class MilestoneTasksInputData {
    private final String studyPlanName;
    private final List<String> milestoneNames;
    private final List<String> milestoneDates;
    private final List<List<String[]>> milestoneIndexToTasks;

    public MilestoneTasksInputData(String studyPlanName,
                                   List<String> milestoneNames,
                                   List<String> milestoneDates,
                                   List<List<String[]>> milestoneIndexToTasks) {
        this.studyPlanName = studyPlanName;
        this.milestoneNames = milestoneNames;
        this.milestoneDates = milestoneDates;
        this.milestoneIndexToTasks = milestoneIndexToTasks;
    }

    List<String> getMilestoneNames() { return milestoneNames; }

    List<String> getMilestoneDates() { return milestoneDates; }

    List<List<String[]>> getMilestoneIndexToTasks() { return milestoneIndexToTasks; }

    String getStudyPlanName() {return studyPlanName; }
}
