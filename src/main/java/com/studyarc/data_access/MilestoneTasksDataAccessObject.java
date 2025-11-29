package com.studyarc.data_access;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksDataAccessObject implements MilestoneTasksDataAccessInterface,
        LoadMilestonesDataAccessInterface {
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
        // needs to save param plan to user
        System.out.println("Not implemented yet");
    }

    @Override
    public ArrayList<StudyPlan> getPlans(String user) {
        studyPlans.add(new StudyPlan("Title", null, "focus"));
        return (ArrayList<StudyPlan>) studyPlans;
    }

    @Override
    public User getUser(String username) {
        User user;
        try {
            user = new User("User", "password", "focus");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return user;
    }


    @Override
    public StudyPlan getPlan(User user, String planName) {
        // hard coded in for now
        List<Milestone> milestones = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task("Task 1", "Sept 20", "Done"));
        tasks.add(new Task("Task 2", "Sept 23", "Not Started"));
        tasks.add(new Task("Task 3", "Sept 26", "In progress"));
        milestones.add( new Milestone("milestone 1", "Oct 3", tasks) );

        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(new Task("Task 4", "Oct 8", "In progress"));
        tasks2.add(new Task("Task 5", "Oct 15", "Not Started"));
        milestones.add( new Milestone("milestone 2", "Oct 20", tasks2) );

        return new StudyPlan("test study plan", milestones, "focus");
    }
}