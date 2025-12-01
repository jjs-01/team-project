package com.studyarc.data_access;

import com.studyarc.entity.*;
import com.studyarc.use_case.add_plan.AddPlanDataAccessInterface;
import com.studyarc.use_case.job_postings.JobPostingsDataAccessInterface;
import com.studyarc.use_case.load_milestones.LoadMilestonesDataAccessInterface;
import com.studyarc.use_case.login.LoginDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.add_reflection.AddReflectionDataAccessInterface;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessinterface;

import java.io.*;
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

    @SuppressWarnings("unchecked")
    private DatabaseAccess() {
        try {
            FileInputStream fileInputStream = new FileInputStream("studyarc-users.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.allUsers = (List<User>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            // If file doesn't exist or can't be read, initialize with empty list
            this.allUsers = new ArrayList<>();
            System.out.println("Could not load users file, starting with empty user list: " + e.getMessage());
        }
        this.user = null;
    }

    public static DatabaseAccess getInstance() {
        return DatabaseAccess.instance == null ?
                (DatabaseAccess.instance = new DatabaseAccess()) :
                DatabaseAccess.instance;
    }

    @Override
    public ArrayList<String> getFocuses() {
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

    public ArrayList<Task> getTasksForMilestone(User user, StudyPlan plan, Milestone milestone) {
        if (milestone != null && milestone.getSubtasks() != null) {
            return new ArrayList<>(milestone.getSubtasks());
        }
        return new ArrayList<>();
    }

    public ArrayList<Milestone> getMilestones(User user, StudyPlan plan) {
        if (plan != null && plan.getMilestones() != null) {
            return new ArrayList<>(plan.getMilestones());
        }
        return new ArrayList<>();
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

    public ArrayList<StudyPlan> generateTestPlans() {
        ArrayList<StudyPlan> plans = new ArrayList<>();

        // Plan 1
        StudyPlan plan1 = new StudyPlan("Plan 1", new ArrayList<>(), "Machine Learning");
        plan1.addResearchPaper(new ResearchPaper(
                "1",
                "Deep Learning for Computer Vision",
                "Smith, J., Johnson, A.",
                "Abstract text here...",
                "http://example.com/paper1"
        ));
        plan1.addResearchPaper(new ResearchPaper(
                "2",
                "Neural Networks Introduction",
                "Williams, B.",
                "Abstract text here...",
                "http://example.com/paper2"
        ));
        plan1.addResearchPaper(new ResearchPaper(
                "3",
                "Advanced CNN Architectures",
                "Brown, C.",
                "Abstract text here...",
                "http://example.com/paper3"
        ));

        Milestone p1m1 = new Milestone("Milestone 1");
        String date = "MM/DD/YYYY";
        String[] taskstatus = {"Not Started", "In Progress", "Completed"};

        Task doStep1 = new Task("Do step1", date, taskstatus[new Random().nextInt(3)]);
        p1m1.getSubtasks().add(doStep1);
        p1m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p1m2 = new Milestone("Milestone 2");
        p1m2.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p1m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan1.getMilestones().add(p1m1);
        plan1.getMilestones().add(p1m2);

        // Plan 2
        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>(), "Artificial Intelligence");
        plan2.addResearchPaper(new ResearchPaper(
                "4",
                "Transformer Models",
                "Davis, M.",
                "Abstract text here...",
                "http://example.com/paper4"
        ));
        plan2.addResearchPaper(new ResearchPaper(
                "5",
                "Attention Mechanisms",
                "Garcia, R.",
                "Abstract text here...",
                "http://example.com/paper5"
        ));
        plan2.addResearchPaper(new ResearchPaper(
                "6",
                "BERT and GPT Models",
                "Martinez, L.",
                "Abstract text here...",
                "http://example.com/paper6"
        ));

        Milestone p2m1 = new Milestone("Milestone 1");
        p2m1.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p2m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p2m2 = new Milestone("Milestone 2");
        p2m2.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p2m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan2.getMilestones().add(p2m1);
        plan2.getMilestones().add(p2m2);

        // Plan 3
        StudyPlan plan3 = new StudyPlan("Plan 3", new ArrayList<>(), "Reinforcement Learning");
        plan3.addResearchPaper(new ResearchPaper(
                "7",
                "Reinforcement Learning Foundations",
                "Nguyen, T.",
                "Abstract text here...",
                "http://example.com/plan3-paper1"
        ));
        plan3.addResearchPaper(new ResearchPaper(
                "8",
                "Policy Gradient Methods",
                "Harrison, E.",
                "Abstract text here...",
                "http://example.com/plan3-paper2"
        ));
        plan3.addResearchPaper(new ResearchPaper(
                "9",
                "Deep Q-Network Advances",
                "Foster, J.",
                "Abstract text here...",
                "http://example.com/plan3-paper3"
        ));

        Milestone p3m1 = new Milestone("Milestone 1");
        p3m1.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p3m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p3m2 = new Milestone("Milestone 2");
        p3m2.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p3m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan3.getMilestones().add(p3m1);
        plan3.getMilestones().add(p3m2);

        // Plan 4
        StudyPlan plan4 = new StudyPlan("Plan 4", new ArrayList<>(), "Computer Vision");
        plan4.addResearchPaper(new ResearchPaper(
                "10",
                "Computer Vision with CNNs",
                "Zhang, W.",
                "Abstract text here...",
                "http://example.com/plan4-paper1"
        ));
        plan4.addResearchPaper(new ResearchPaper(
                "11",
                "Image Segmentation Techniques",
                "Lopez, D.",
                "Abstract text here...",
                "http://example.com/plan4-paper2"
        ));
        plan4.addResearchPaper(new ResearchPaper(
                "12",
                "Vision Transformers Explained",
                "Khan, R.",
                "Abstract text here...",
                "http://example.com/plan4-paper3"
        ));

        Milestone p4m1 = new Milestone("Milestone 1");
        p4m1.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p4m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p4m2 = new Milestone("Milestone 2");
        p4m2.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p4m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan4.getMilestones().add(p4m1);
        plan4.getMilestones().add(p4m2);

        // Plan 5
        StudyPlan plan5 = new StudyPlan("Plan 5", new ArrayList<>(), "Data Mining");
        plan5.addResearchPaper(new ResearchPaper(
                "13",
                "Data Mining Algorithms",
                "Singh, P.",
                "Abstract text here...",
                "http://example.com/plan5-paper1"
        ));
        plan5.addResearchPaper(new ResearchPaper(
                "14",
                "Clustering Techniques in Big Data",
                "Adams, L.",
                "Abstract text here...",
                "http://example.com/plan5-paper2"
        ));
        plan5.addResearchPaper(new ResearchPaper(
                "15",
                "Dimensionality Reduction Methods",
                "Brown, C.",
                "Abstract text here...",
                "http://example.com/plan5-paper3"
        ));

        Milestone p5m1 = new Milestone("Milestone 1");
        p5m1.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p5m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p5m2 = new Milestone("Milestone 2");
        p5m2.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p5m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan5.getMilestones().add(p5m1);
        plan5.getMilestones().add(p5m2);

        // Plan 6
        StudyPlan plan6 = new StudyPlan("Plan 6", new ArrayList<>(), "Robotics");
        plan6.addResearchPaper(new ResearchPaper(
                "16",
                "Robotics Motion Planning",
                "Ivanov, M.",
                "Abstract text here...",
                "http://example.com/plan6-paper1"
        ));
        plan6.addResearchPaper(new ResearchPaper(
                "17",
                "SLAM Techniques Review",
                "Wilson, G.",
                "Abstract text here...",
                "http://example.com/plan6-paper2"
        ));
        plan6.addResearchPaper(new ResearchPaper(
                "18",
                "Human–Robot Interaction Models",
                "Chan, S.",
                "Abstract text here...",
                "http://example.com/plan6-paper3"
        ));

        Milestone p6m1 = new Milestone("Milestone 1");
        p6m1.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p6m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p6m2 = new Milestone("Milestone 2");
        p6m2.getSubtasks().add(new Task("Do step1", date, taskstatus[new Random().nextInt(3)]));
        p6m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan6.getMilestones().add(p6m1);
        plan6.getMilestones().add(p6m2);

        // Add all plans to list
        plans.add(plan1);
        plans.add(plan2);
        plans.add(plan3);
        plans.add(plan4);
        plans.add(plan5);
        plans.add(plan6);

        return plans;
    }

    @Override
    public boolean registerUser(User u) {
        if (u != null && !allUsers.contains(u)) {
            allUsers.add(u);
            save();
            return true;
        }
        return false;
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
        return this.user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public StudyPlan getPlan(User user, String planName) {
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

    public void savePlan(User user, StudyPlan plan) {
        save();
    }

    @Override
    public void savePlan(StudyPlan plan) {
        save();
    }

    public User getCurrentUser() {
        return this.user;
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
}