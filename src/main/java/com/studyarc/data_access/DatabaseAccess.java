package com.studyarc.data_access;

import com.studyarc.entity.*;
import com.studyarc.use_case.add_plan.AddPlanDataAccessInterface;
import com.studyarc.use_case.job_postings.JobPostingsDataAccessInterface;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.login.LoginDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.add_reflection.AddReflectionDataAccessInterface;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessinterface;
import com.studyarc.use_case.search_research_papers.SearchResearchPapersDataAccessInterface;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersDataAccessInterface;
import com.studyarc.use_case.add_papers_to_plan.AddPapersToPlanDataAccessInterface;
import com.studyarc.interface_adapter.search_research_papers.CoreResearchAdapter;
import com.studyarc.data_access.core.COREAPIClient;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class DatabaseAccess implements JobPostingsDataAccessInterface,
        LoginDataAccessInterface,
        MilestoneTasksDataAccessInterface,
        LoadMilestonesDataAccessInterface,
        AddReflectionDataAccessInterface,
        TrackPlanDataAccessinterface,
        AddPlanDataAccessInterface,
        SearchResearchPapersDataAccessInterface,
        ViewingResearchPapersDataAccessInterface,
        AddPapersToPlanDataAccessInterface {

    private static DatabaseAccess instance;
    private User user;
    private List<User> allUsers;
    private CoreResearchAdapter researchAdapter;

    private DatabaseAccess() {
        File f = new File("studyarc-users.ser");
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                //noinspection unchecked
                this.allUsers = (List<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                System.out.println("Could not load users file, starting with empty user list: " + e.getMessage());
                this.allUsers = new ArrayList<>();
            }
        } else {
            // first runs: no file yet
            this.allUsers = new ArrayList<>();
        }
        this.user = null;

        try {
            Dotenv dotenv = Dotenv.load();
            String apiKey = dotenv.get("CORE_API_KEY");
            if (apiKey != null && !apiKey.isEmpty()) {
                COREAPIClient apiClient = new COREAPIClient(apiKey);
                this.researchAdapter = new CoreResearchAdapter(apiClient);
            }
        } catch (Exception e) {
            System.out.println("CORE API not configured: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> getFocuses() {
        ArrayList<String> focuses = new ArrayList<>();
        ArrayList<StudyPlan> allStudyPlans = this.getPlans();

        for (StudyPlan studyPlan : allStudyPlans) {
            String focus = studyPlan.getFocus();
            if (focus != null && !focuses.contains(focus)) {
                focuses.add(focus);
            }
        }

        // Remove duplicates
        Set<String> set = new HashSet<>(focuses);
        focuses = new ArrayList<>(set);

        return focuses;
    }

    public ArrayList<StudyPlan> getPlans(String username) {
        if (username != null) {
            for (User u : allUsers) {
                if (u.getUsername().equals(username)) {
                    return u.getStudyPlans();
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public ArrayList<StudyPlan> getPlans() {
        if (user != null) {
            return user.getStudyPlans();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean registerUser(String username, String password) {
        try {
            User newUser = new User(username, password);
            this.user = newUser;
            this.allUsers.add(newUser);
            this.save();
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        if (username != null) {
            for (User u : allUsers) {
                if (u.getUsername().equals(username)) {
                    return u;
                }
            }
        }
        return null;
    }

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
    public void addPlan(StudyPlan plan) {
        if (user != null && plan != null) {
            user.getStudyPlans().add(plan);
            save();
        }
    }

    @Override
    public void savePlan(StudyPlan plan) {
        save();
    }

    // SearchResearchPapersDataAccessInterface methods
    @Override
    public SearchResearchPapersDataAccessInterface.SearchResult searchPapers(String query, int limit, int offset) {
        if (researchAdapter != null) {
            return researchAdapter.searchPapers(query, limit, offset);
        }
        throw new IllegalStateException("CORE API not configured. Please add CORE_API_KEY to .env file");
    }

    @Override
    public SearchResearchPapersDataAccessInterface.SearchResult searchPapersByYear(String query, int yearFrom, int yearTo, int limit) {
        if (researchAdapter != null) {
            return researchAdapter.searchPapersByYear(query, yearFrom, yearTo, limit);
        }
        throw new IllegalStateException("CORE API not configured. Please add CORE_API_KEY to .env file");
    }

    @Override
    public ResearchPaper getPaperById(String paperId) {
        if (researchAdapter != null) {
            return researchAdapter.getPaperById(paperId);
        }
        throw new IllegalStateException("CORE API not configured. Please add CORE_API_KEY to .env file");
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
            System.err.println("Error saving users to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveAllPlansForUser(ArrayList<StudyPlan> plans) {
        if (user != null) {
            this.user.setStudyPlans(plans);
            this.save();
        }
    }
}