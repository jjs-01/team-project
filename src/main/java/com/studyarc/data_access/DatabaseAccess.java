package com.studyarc.data_access;

import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Task;
import com.studyarc.entity.User;
import com.studyarc.use_case.add_plan.AddPlanDataAccessInterface;
import com.studyarc.use_case.job_postings.JobPostingsDataAccessInterface;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.login.LoginDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.add_reflection.AddReflectionDataAccessInterface;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessinterface;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class DatabaseAccess implements JobPostingsDataAccessInterface,
        LoginDataAccessInterface,
        MilestoneTasksDataAccessInterface,
        LoadMilestonesDataAccessInterface,
        AddReflectionDataAccessInterface,
        TrackPlanDataAccessinterface,
        AddPlanDataAccessInterface {
    private static DatabaseAccess instance;
    private User user;
    private List<User> allUsers;
    private ArrayList<String> focuses = new ArrayList<>();

//    @SuppressWarnings("unchecked")
//    private DatabaseAccess(){
//        try {
//            FileInputStream fileInputStream = new FileInputStream("studyarc-users.ser");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            this.allUsers = (List<User>) objectInputStream.readObject();
//        } catch (IOException | ClassNotFoundException | ClassCastException e) {
//            e.printStackTrace();
//        }
//        this.user = null;
//    }

    private DatabaseAccess() {
        File f = new File("studyarc-users.ser");
        if (f.exists()) {
            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(f))) {
                //noinspection unchecked
                this.allUsers = (List<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                e.printStackTrace();
                this.allUsers = new ArrayList<>();
            }
        } else {
            // first runs: no file yet
            this.allUsers = new ArrayList<>();
        }
        this.user = null;
    }

    @Override
    public ArrayList<String> getFocuses() {
//        System.out.println("Checking user:" + user);
//        System.out.println("Checking user:" + user.getUsername());
//        System.out.println("Checking user:" + user.getStudyPlans());

        ArrayList<StudyPlan> allStudyPlans = this.getPlans();

        for (StudyPlan studyPlan : allStudyPlans) {
            if (!focuses.contains(studyPlan.getFocus())) {
                focuses.add(studyPlan.getFocus());
            }
        }

        // removes duplicates
        Set<String> set = new HashSet<>(focuses);
        focuses = new ArrayList<>(set);

        return focuses;
    }

    @Override
    public ArrayList<StudyPlan> getPlans() {
        return this.user.getStudyPlans();
    }


    @Override
    public boolean registerUser(String username, String password) {
        try {
            User newUser = new User(username, password);
            this.user = newUser;
            this.allUsers.add(newUser);
            this.save();
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }

    @Override
    public User getUser(String username) {
        User u = null;
        for (User allUser : this.allUsers) {
            if (allUser.getUsername().equals(username)) {
                u = allUser;
                return u;
            }
        }
        return u;
    }

    public void setUser(User u) {
        this.user = u;
    }

    @Override
    public StudyPlan getPlan(String planName) {
        ArrayList<StudyPlan> userStudyPlans = getPlans();
        for (StudyPlan plan : userStudyPlans) {
            if (plan.getTitle().equals(planName)) {
                return plan;
            }
        }
        return null;
        // OR throw new IllegalArgumentException("Plan does not exist");
    }


    @Override
    public void addPlan(StudyPlan plan) {
        user.getStudyPlans().add(plan);
        System.out.println("add plan not implemented yet");
    }

    @Override
    public void savePlan(StudyPlan plan) {
    }

    public static DatabaseAccess getInstance() {
        return DatabaseAccess.instance == null ? (DatabaseAccess.instance = new DatabaseAccess()) : DatabaseAccess.instance;
    }

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("studyarc-users.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.allUsers);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAllPlansForUser(ArrayList<StudyPlan> plans) {
        this.user.setStudyPlans(plans);
        this.save();
    }

}

