package com.studyarc.use_case.milestone_tasks;

import java.util.List;
import java.util.Map;

public class MilestoneTasksInputData {
    private final String studyPlanName;
    private final List<String> milestoneNames;
    private final List<String> milestoneDates;
    private final Map<Integer,List<String[]>> milestoneIndexToTasks;

    public MilestoneTasksInputData(String studyPlanName,
                                   List<String> milestoneNames,
                                   List<String> milestoneDates,
                                   Map<Integer,List<String[]>> milestoneIndexToTasks) {
        this.studyPlanName = studyPlanName;
        this.milestoneNames = milestoneNames;
        this.milestoneDates = milestoneDates;
        this.milestoneIndexToTasks = milestoneIndexToTasks;
    }

    List<String> getMilestoneNames() { return milestoneNames; }

    List<String> getMilestoneDates() { return milestoneDates; }

    Map<Integer,List<String[]>> getMilestoneIndexToTasks() { return milestoneIndexToTasks; }

    String getStudyPlanName() {return studyPlanName; }
}
