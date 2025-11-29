package com.studyarc.entity;

import java.util.ArrayList;
import java.util.List;

public class StudyPlan {
    private String title;
    private List<Milestone> milestones;
    private final List<Reflection> reflections = new ArrayList<>();
    private String focus;

    public StudyPlan(String title, List<Milestone> milestones, String focus) {
        this.title = title;
        this.milestones = milestones;
        this.focus = focus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public List<Reflection> getReflections() {
        return reflections;
    }

    public void addReflection(Reflection reflection) {
        this.reflections.add(reflection);
    }

    public String getFocus() { return focus; }

    public void setFocus(String focus) { this.focus = focus; }
}
