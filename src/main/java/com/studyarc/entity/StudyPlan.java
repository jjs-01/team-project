package com.studyarc.entity;

import java.util.ArrayList;

public class StudyPlan {
    private String title;
    private ArrayList<Milestone> milestones;
    private Reflection reflection;

    public StudyPlan(String title, ArrayList<Milestone> milestones) {
        this.title = title;
        this.milestones = milestones;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(ArrayList<Milestone> milestones) {
        this.milestones = milestones;
    }
}
