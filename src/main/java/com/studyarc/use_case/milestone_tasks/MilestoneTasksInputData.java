package com.studyarc.use_case.milestone_tasks;

import java.util.List;

public class MilestoneTasksInputData {
    private final String studyPlanName;
    private final List<String> milestoneNames;
    private final List<String> milestoneDates;
    private final List<List<String[]>> milestoneIndexToTasks;
    private final String focus;

    public MilestoneTasksInputData(String studyPlanName,
                                   List<String> milestoneNames,
                                   List<String> milestoneDates,
                                   List<List<String[]>> milestoneIndexToTasks, String focus) {
        this.studyPlanName = studyPlanName;
        this.milestoneNames = milestoneNames;
        this.milestoneDates = milestoneDates;
        this.milestoneIndexToTasks = milestoneIndexToTasks;
        this.focus = focus;
    }

    List<String> getMilestoneNames() { return milestoneNames; }

    List<String> getMilestoneDates() { return milestoneDates; }

    List<List<String[]>> getMilestoneIndexToTasks() { return milestoneIndexToTasks; }

    String getStudyPlanName() {return studyPlanName; }

    String getFocus() {return focus;}
}
