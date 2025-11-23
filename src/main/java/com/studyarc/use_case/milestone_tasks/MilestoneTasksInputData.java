package com.studyarc.use_case.milestone_tasks;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MilestoneTasksInputData {
    private final String studyPlanName;
    private final ArrayList<Milestone> milestones;

    public MilestoneTasksInputData(String studyPlanName, Map<String, String> milestoneData,
                                   Map<String, List<String[]>> milestonesToTasksData) {
        this.studyPlanName = studyPlanName;
        milestones = new ArrayList<>();
        for (Map.Entry<String, String> milestoneInfo : milestoneData.entrySet()) {
            List<Task> tasks = new ArrayList<>();
            for (String[] taskData : milestonesToTasksData.get(milestoneInfo.getKey())) {
                tasks.add(new Task(taskData[0], taskData[1], taskData[2]));
            }

            Milestone milestone = new Milestone(milestoneInfo.getKey(), milestoneInfo.getValue(), tasks);
            milestones.add(milestone);
        }
    }

    ArrayList<Milestone> getMilestones() { return milestones; }

    String getStudyPlanName() {return studyPlanName; }
}
