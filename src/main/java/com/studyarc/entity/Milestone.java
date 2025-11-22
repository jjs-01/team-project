package com.studyarc.entity;

import java.util.ArrayList;

public class Milestone {
    private String name;
    private ArrayList<Task> subtasks;
    private String dueDate;

    public Milestone(String title) {
        this.name = title;
        this.subtasks = new ArrayList<>();
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public ArrayList<Task> getSubtasks() {
        return subtasks;
    }

}
