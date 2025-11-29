package com.studyarc.use_case.load_milestones;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadMilestonesOutputData {
    private final List<Milestone> milestones;

    public LoadMilestonesOutputData(List<Milestone> milestones) {
        this.milestones = milestones;
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
