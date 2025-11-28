package com.studyarc.entity;

import java.util.ArrayList;
import java.util.List;

public class StudyPlan {
    private String title;
    private ArrayList<Milestone> milestones;
    private final List<Reflection> reflections = new ArrayList<>();
    private final List<ResearchPaper> researchPapers = new ArrayList<>();

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

    public List<Reflection> getReflections() {
        return reflections;
    }

    public void addReflection(Reflection reflection) {
        this.reflections.add(reflection);
    }

    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public void addResearchPaper(ResearchPaper paper) {
        this.researchPapers.add(paper);
    }

}
