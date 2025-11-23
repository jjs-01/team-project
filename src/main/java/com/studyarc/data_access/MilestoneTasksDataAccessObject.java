package com.studyarc.data_access;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;

import java.util.ArrayList;
import java.util.Map;

public class MilestoneTasksDataAccessObject implements MilestoneTasksDataAccessInterface {
    @Override
    public ArrayList<Task> getTasksForMilestone(User user, StudyPlan plan, Milestone milestone) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Milestone> getMilestones(User user, StudyPlan plan) {
        return new ArrayList<>();
    }

    @Override
    public void savePlan(User user, StudyPlan plan) {
        System.out.println("Not implemented yet");
    }

    @Override
    public ArrayList<StudyPlan> getPlans(User user) {return null;}

    @Override
    public User getUser(String username) {
        return null;
    }
}