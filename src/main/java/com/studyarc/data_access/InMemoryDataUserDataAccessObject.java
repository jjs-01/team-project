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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/**
 * DataAccessObject for test cases
 */
public class InMemoryDataUserDataAccessObject implements JobPostingsDataAccessInterface,
        LoginDataAccessInterface,
        MilestoneTasksDataAccessInterface,
        LoadMilestonesDataAccessInterface,
        AddReflectionDataAccessInterface,
        TrackPlanDataAccessinterface,
        AddPlanDataAccessInterface {

    private User user;
    private final List<User> allUsers = new ArrayList<>();
    private final ArrayList<String> focuses = new ArrayList<>();

    @Override
    public void addPlan(StudyPlan plan) {
        user.getStudyPlans().add(plan);
    }

    @Override
    public ArrayList<String> getFocuses() {
        return focuses;
    }

    @Override
    public boolean registerUser(String username, String password) {
        for (User currUser : allUsers) {
            if (currUser.getUsername().equals(username)) {
                return false;
            }
        }

        try {
            User newUser = new User(username, password);
            allUsers.add(newUser);
            setUser(newUser);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        return this.user;
    }

    @Override
    public void setUser(User u) {
        this.user = u;
    }

    @Override
    public StudyPlan getPlan(String planName) {
        if (user != null && planName != null) {
            ArrayList<StudyPlan> userStudyPlans = user.getStudyPlans();
            for (StudyPlan plan : userStudyPlans) {
                if (plan.getTitle().equals(planName)) {
                    return plan;
                }
            }
        }
        return null;
    }

    @Override
    public void save() {
        // does not need to serialize since this is for in memory
    }

    @Override
    public void saveAllPlansForUser(ArrayList<StudyPlan> plans) {
        this.user.setStudyPlans(plans);
    }

    @Override
    public ArrayList<StudyPlan> getPlans() {
        return user.getStudyPlans();
    }

}
