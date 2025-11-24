package com.studyarc.interface_adapter.milestone_tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilestoneTasksState {
    private final List<String> milestoneNames = new ArrayList<>();
    private final List<String> milestoneDates = new ArrayList<>();
    private final Map<Integer, List<String[]>> milestoneIndexToTasks = new HashMap<>();

    public void addMilestone(int milestoneIndex, String name, String date) {
        milestoneNames.add(milestoneIndex, name);
        milestoneDates.add(milestoneIndex, date);
        milestoneIndexToTasks.put(milestoneIndex, new ArrayList<>());
    }

    public void addTask(int milestoneIndex, String name, String date, String status) {
        if (milestoneIndexToTasks.containsKey(milestoneIndex)) {
            String[] taskInfo = {name, date, status};
            milestoneIndexToTasks.get(milestoneIndex).add(taskInfo);
        }
    }

    public void setMilestoneName(int index, String newName) {
        milestoneNames.set(index, newName);
    }

    public void setMilestoneDate(int index, String newDate) {
        milestoneDates.set(index, newDate);
    }

    public void setTaskName(int milestoneIndex, int taskIndex, String newName) {
        String[] taskInfo = milestoneIndexToTasks.get(milestoneIndex).get(taskIndex);
        taskInfo[0] = newName;
    }

    public void setTaskDate(int milestoneIndex, int taskIndex, String newDate) {
        String[] taskInfo = milestoneIndexToTasks.get(milestoneIndex).get(taskIndex);
        taskInfo[1] = newDate;
    }

    public void setTaskStatus(int milestoneIndex, int taskIndex, String newStatus) {
        String[] taskInfo = milestoneIndexToTasks.get(milestoneIndex).get(taskIndex);
        taskInfo[2] = newStatus;
    }

    // NOT implemented yet
//    public void removeMilestone(int index) {
//        milestoneNames.remove(index);
//        milestoneDates.remove(index);
//        milestoneIndexToTasks.remove(index);
//    }
//
//    public void removeTask(int milestoneIndex, int taskIndex) {
//        List<String[]> tasks = milestoneIndexToTasks.get(milestoneIndex);
//    }

    public List<String[]> getTasks(int milestoneIndex) {
        return milestoneIndexToTasks.get(milestoneIndex);
    }

    public Map<Integer, List<String[]>> getMilestoneIndextoTasks() {
        return milestoneIndexToTasks;
    }

    public List<String> getMilestoneNames() {
        return milestoneNames;
    }

    public List<String> getMilestoneDates() {
        return milestoneDates;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newlineComma = "', \n";
        result.append("MilestoneTasksState{[");

        for (int i = 0; i < milestoneNames.size(); i++) {
            result.append("milestoneName='")
                    .append(milestoneNames.get(i))
                    .append(newlineComma);
            result.append("dueDate='")
                    .append(milestoneDates.get(i))
                    .append("', \n");
            result.append("tasks=[");


            List<String[]> currTasks = getTasks(i);
            for (String[] task : currTasks) {
                result.append("[taskName='")
                        .append(task[0])
                        .append(newlineComma);
                result.append("dueDate='")
                        .append(task[1])
                        .append(newlineComma);
                result.append("status='")
                        .append(task[2])
                        .append(newlineComma);
            }
            result.append("], \n");
        }
        result.append("]");

        return result.toString();
    }
}