package com.studyarc.data_access;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;
import com.studyarc.use_case.add_plan.AddPlanDataAccessInterface;
import com.studyarc.use_case.add_reflection.AddReflectionDataAccessInterface;
import com.studyarc.use_case.job_postings.JobPostingsDataAccessInterface;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.login.LoginDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessinterface;

import java.util.ArrayList;

public class InMemoryDataUserDataAccessObject implements JobPostingsDataAccessInterface,
        LoginDataAccessInterface,
        MilestoneTasksDataAccessInterface,
        LoadMilestonesDataAccessInterface,
        AddReflectionDataAccessInterface,
        TrackPlanDataAccessinterface,
        AddPlanDataAccessInterface {

    @Override
    public void addPlan(StudyPlan plan) {

    }

    @Override
    public ArrayList<String> getFocuses() {
        return null;
    }

    @Override
    public boolean registerUser(User u) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void setUser(User u) {

    }

    @Override
    public void savePlan(StudyPlan plan) {

    }

    @Override
    public StudyPlan getPlan(String planName) {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public ArrayList<StudyPlan> getPlans() {
        return null;
    }

    @Override
    public ArrayList<StudyPlan> generateTestPlans() {
        return null;
    }
}
