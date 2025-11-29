package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadMilestonesOutputData {
    private final String studyPlanName;
    private final String focus;
    private final List<Milestone> milestones;

    public LoadMilestonesOutputData(String studyPlanName, String focus, List<Milestone> milestones) {
        this.studyPlanName = studyPlanName;
        this.focus = focus;
        this.milestones = milestones;
    }

    public String getStudyPlanName() {
        return studyPlanName;
    }

    public String getFocus() {
        return focus;
    }

    public List<String[]> getMilestoneInfo() {
        List<String[]> result = new ArrayList<>();

        for (Milestone milestone : milestones) {
            String[] nameDateList = {milestone.getTitle(), milestone.getDueDate()};
            result.add(nameDateList);
        }

        return result;
    }

    public List<List<String[]>> getTaskInfo() {
        List<List<String[]>> result = new ArrayList<>();

        for (Milestone milestone : milestones) {
            List<String[]> milestoneTaskInfo = new ArrayList<>();
            List<Task> tasksList = milestone.getSubtasks();

            for (Task task : tasksList) {
                String[] taskInfo = {task.getName(), task.getDueDate(), task.getStatus()};
                milestoneTaskInfo.add(taskInfo);
            }

            result.add(milestoneTaskInfo);
        }
        return result;
    }
}
