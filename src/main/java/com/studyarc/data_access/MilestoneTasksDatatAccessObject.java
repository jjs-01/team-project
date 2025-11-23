package com.studyarc.data_access;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;

import java.util.ArrayList;
import java.util.Map;

public class MilestoneTasksDatatAccessObject implements MilestoneTasksDataAccessInterface {
    @Override
    public ArrayList<Task> getTasksForMilestone(User user, StudyPlan plan, Milestone milestone) {
        return null;
    }

    @Override
    public ArrayList<Milestone> getMilestones(User user, StudyPlan plan) {
        return null;
    }

    @Override
    public void savePlan(User user, StudyPlan plan, Map<Milestone, ArrayList<Task>> milestonesToTasks) {

    }
}
