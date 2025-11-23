package com.studyarc.interface_adapter.milestone_tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilestoneTasksState {
    private List<String> milestoneNames = new ArrayList<>();
    private List<String> milestoneDates = new ArrayList<>();
    private Map<Integer, List<String[]>> milestonesToTasks = new HashMap<>();


    // private List<String[]>

    public void addMilestone(int milestoneIndex, String name, String date) {
        milestoneNames.add(milestoneIndex, name);
        milestoneDates.add(milestoneIndex, date);
        milestonesToTasks.put(milestoneIndex, new ArrayList<>());
    }

    public void addTask(int milestoneIndex, String[] taskInfo) {
        if (milestonesToTasks.containsKey(milestoneIndex)) {
            milestonesToTasks.get(milestoneIndex).add(taskInfo);
        }
    }

    public void setMilestoneName(int index, String newName) {
        milestoneNames.set(index, newName);
    }

    public void removeMilestone(int index) {
        milestoneNames.remove(index);
        milestoneDates.remove(index);
        milestonesToTasks.remove(index);
    }

    public void removeTask(int milestoneIndex, String taskName) {
        List<String[]> tasks = milestonesToTasks.get(milestoneIndex);
    }

    public List<String[]> getTasks(int milestoneIndex) {
        return milestonesToTasks.get(milestoneIndex);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MilestoneTasksState{[");

        for (int i = 0; i < milestoneNames.size(); i++) {
            result.append("milestoneName='")
                    .append(milestoneNames.get(i))
                    .append("', \n");
            result.append("dueDate='")
                    .append(milestoneDates.get(i))
                    .append("', \n");
            result.append("tasks=[");


            List<String[]> currTasks = getTasks(i);
            for (String[] task : currTasks) {
                result.append("[taskName='")
                        .append(task[0])
                        .append("', \n");
                result.append("dueDate='")
                        .append(task[1])
                        .append("', \n");
                result.append("status='")
                        .append(task[2])
                        .append("'], \n");
            }
            result.append("], \n");
        }
        result.append("] BLAH");

        return result.toString();
    }
}