package com.studyarc.data_access;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksDataAccessObject implements MilestoneTasksDataAccessInterface, LoadMilestonesDataAccessInterface {
    List<StudyPlan> studyPlans = new ArrayList<>();

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
    public ArrayList<StudyPlan> getPlans(String user) {
        studyPlans.add(new StudyPlan("Title", null));
        return (ArrayList<StudyPlan>) studyPlans;
    }

    @Override
    public User getUser(String username) {
        return new User();
    }

    @Override
    public StudyPlan getPlan(User user, String planName) {
        return null;
    }
}