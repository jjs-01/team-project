package com.studyarc.entity;

import java.util.ArrayList;
import java.util.List;

public class Milestone {
    private String dueDate;
    private String name;
    private final List<Task> subtasks;
  
    public Milestone(String title) {
        this.name = title;
        this.subtasks = new ArrayList<>();
    }

    public Milestone(String name, String dueDate) {
//        if ("".equals(name)) {
//            throw new IllegalArgumentException("Name cannot be empty.");
//        }
        this.name = name;
        this.dueDate = dueDate;
        this.subtasks = new ArrayList<>();
    }

    public Milestone(String name, String dueDate, List<Task> subtasks) {
//        if ("".equals(name)) {
//            throw new IllegalArgumentException("Name cannot be empty.");
//        }
        this.name = name;
        this.dueDate = dueDate;
        this.subtasks = subtasks;
    }

    public void addTask(Task task) {
        subtasks.add(task);
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void setTitle(String name) {
//        if ("".equals(name)) {
//            throw new IllegalArgumentException("Name cannot be empty.");
//        }
        this.name = name;
    }

    public String getTitle() {
        return name;
    }

    public void setDueDate(String date) {
        dueDate = date;
    }

    public String getDueDate() {
        return dueDate;
    }
}
