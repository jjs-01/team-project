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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataUserDataAccessObject implements JobPostingsDataAccessInterface,
        LoginDataAccessInterface,
        MilestoneTasksDataAccessInterface,
        LoadMilestonesDataAccessInterface,
        AddReflectionDataAccessInterface,
        TrackPlanDataAccessinterface,
        AddPlanDataAccessInterface {

    private User user;
    private List<User> allUsers;
    private ArrayList<String> focuses = new ArrayList<>();

    @Override
    public void addPlan(StudyPlan plan) {

    }

    @Override
    public ArrayList<String> getFocuses() {
        return null;
    }

    @Override
    public boolean registerUser(String username, String password) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void setUser(User u) {
        this.user = u;
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
    public void saveAllPlansForUser(ArrayList<StudyPlan> plans) {

    }

    @Override
    public ArrayList<StudyPlan> getPlans() {
        return user.getStudyPlans();
    }

}
