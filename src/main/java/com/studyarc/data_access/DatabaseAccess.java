package com.studyarc.data_access;

import com.studyarc.entity.*;
import com.studyarc.use_case.job_postings.JobPostingsDataAccessInterface;
import com.studyarc.use_case.login.LoginDataAccessInterface;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksDataAccessInterface;
import com.studyarc.use_case.add_reflection.AddReflectionDataAccessInterface;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessinterface;

import java.util.*;

public class DatabaseAccess implements JobPostingsDataAccessInterface, LoginDataAccessInterface, MilestoneTasksDataAccessInterface, AddReflectionDataAccessInterface, TrackPlanDataAccessinterface {

    @Override
    public ArrayList<String> getFocuses() {
        return null;
    }

    @Override
    public ArrayList<Task> getTasksForMilestone(User user, StudyPlan plan, Milestone milestone) {
        return null;
    }

    @Override
    public ArrayList<Milestone> getMilestones(User user, StudyPlan plan) {
        return null;
    }

    @Override
    public ArrayList<StudyPlan> getPlans(String username) {
        return this.generateTestPlans();
    }

    public ArrayList<StudyPlan> generateTestPlans() {
        ArrayList<StudyPlan> plans = new ArrayList<>();

        // Plan 1
        StudyPlan plan1 = new StudyPlan("Plan 1", new ArrayList<>());
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

        //Temp Date, change it later;
        String date = "MM/DD/YYYY";
        String[] taskstatus = {"Not Started", "In Progress", "Completed"};
            System.out.println(taskstatus[new Random().nextInt(3)]);
        Task doStep1 = new Task("Do step1", date, taskstatus[new Random().nextInt(3)]);
        p1m1.getSubtasks().add(doStep1);
        p1m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p1m2 = new Milestone("Milesone 2");
        p1m2.getSubtasks().add(doStep1);
        p1m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan1.getMilestones().add(p1m1);
        plan1.getMilestones().add(p1m2);

        // Plan 2
        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>());

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
        p2m1.getSubtasks().add(doStep1);
        p2m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p2m2 = new Milestone("Milestone 2");
        p2m2.getSubtasks().add(doStep1);
        p2m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan2.getMilestones().add(p2m1);
        plan2.getMilestones().add(p2m2);

        // Plan 3
        StudyPlan plan3 = new StudyPlan("Plan 3", new ArrayList<>());

        plan3.addResearchPaper(new ResearchPaper(
                "1",
                "Reinforcement Learning Foundations",
                "Nguyen, T.",
                "Abstract text here...",
                "http://example.com/plan3-paper1"
        ));
        plan3.addResearchPaper(new ResearchPaper(
                "2",
                "Policy Gradient Methods",
                "Harrison, E.",
                "Abstract text here...",
                "http://example.com/plan3-paper2"
        ));
        plan3.addResearchPaper(new ResearchPaper(
                "3",
                "Deep Q-Network Advances",
                "Foster, J.",
                "Abstract text here...",
                "http://example.com/plan3-paper3"
        ));

        Milestone p3m1 = new Milestone("Milestone 1");
        p3m1.getSubtasks().add(doStep1);
        p3m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p3m2 = new Milestone("Milestone 2");
        p3m2.getSubtasks().add(doStep1);
        p3m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan3.getMilestones().add(p3m1);
        plan3.getMilestones().add(p3m2);

        // plan 4
        StudyPlan plan4 = new StudyPlan("Plan 4", new ArrayList<>());

        plan4.addResearchPaper(new ResearchPaper(
                "1",
                "Computer Vision with CNNs",
                "Zhang, W.",
                "Abstract text here...",
                "http://example.com/plan4-paper1"
        ));
        plan4.addResearchPaper(new ResearchPaper(
                "2",
                "Image Segmentation Techniques",
                "Lopez, D.",
                "Abstract text here...",
                "http://example.com/plan4-paper2"
        ));
        plan4.addResearchPaper(new ResearchPaper(
                "3",
                "Vision Transformers Explained",
                "Khan, R.",
                "Abstract text here...",
                "http://example.com/plan4-paper3"
        ));

        Milestone p4m1 = new Milestone("Milestone 1");
        p4m1.getSubtasks().add(doStep1);
        p4m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p4m2 = new Milestone("Milestone 2");
        p4m2.getSubtasks().add(doStep1);
        p4m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan4.getMilestones().add(p4m1);
        plan4.getMilestones().add(p4m2);
        // Plan 5
        StudyPlan plan5 = new StudyPlan("Plan 5", new ArrayList<>());

        plan5.addResearchPaper(new ResearchPaper(
                "1",
                "Data Mining Algorithms",
                "Singh, P.",
                "Abstract text here...",
                "http://example.com/plan5-paper1"
        ));
        plan5.addResearchPaper(new ResearchPaper(
                "2",
                "Clustering Techniques in Big Data",
                "Adams, L.",
                "Abstract text here...",
                "http://example.com/plan5-paper2"
        ));
        plan5.addResearchPaper(new ResearchPaper(
                "3",
                "Dimensionality Reduction Methods",
                "Brown, C.",
                "Abstract text here...",
                "http://example.com/plan5-paper3"
        ));

        Milestone p5m1 = new Milestone("Milestone 1");
        p5m1.getSubtasks().add(doStep1);
        p5m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p5m2 = new Milestone("Milestone 2");
        p5m2.getSubtasks().add(doStep1);
        p5m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan5.getMilestones().add(p5m1);
        plan5.getMilestones().add(p5m2);

        //plan 6
        StudyPlan plan6 = new StudyPlan("Plan 6", new ArrayList<>());

        plan6.addResearchPaper(new ResearchPaper(
                "1",
                "Robotics Motion Planning",
                "Ivanov, M.",
                "Abstract text here...",
                "http://example.com/plan6-paper1"
        ));
        plan6.addResearchPaper(new ResearchPaper(
                "2",
                "SLAM Techniques Review",
                "Wilson, G.",
                "Abstract text here...",
                "http://example.com/plan6-paper2"
        ));
        plan6.addResearchPaper(new ResearchPaper(
                "3",
                "Human–Robot Interaction Models",
                "Chan, S.",
                "Abstract text here...",
                "http://example.com/plan6-paper3"
        ));

        Milestone p6m1 = new Milestone("Milestone 1");
        p6m1.getSubtasks().add(doStep1);
        p6m1.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        Milestone p6m2 = new Milestone("Milestone 2");
        p6m2.getSubtasks().add(doStep1);
        p6m2.getSubtasks().add(new Task("Do step2", date, taskstatus[new Random().nextInt(3)]));

        plan6.getMilestones().add(p6m1);
        plan6.getMilestones().add(p6m2);

        //add all plans to plans
        plans.add(plan1);
        plans.add(plan2);
        plans.add(plan3);
        plans.add(plan4);
        plans.add(plan5);
        plans.add(plan6);

        return plans;

    }//Generate TestPlans for testing TrackPlanusecase and Deletplan.

    @Override
    public boolean registerUser(User u) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public StudyPlan getPlan(User user, String planName) {return null;}

    @Override
    public void savePlan(User user, StudyPlan plan) {
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}

