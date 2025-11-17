package com.studyarc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Milestone {
    private Date dueDate;
    private String name;
    private final List<Task> subtasks;

    public Milestone(String name, Date dueDate) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
        this.dueDate = dueDate;
        this.subtasks = new ArrayList<>();
    }

    public Milestone(String name, Date dueDate, List<Task> subtasks) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
        this.dueDate = dueDate;
        this.subtasks = subtasks;
    }

    public void addTask(Task task) {
        subtasks.add(task);
    }

    public List<Task> getTasks() {
        return subtasks;
    }

    public void setName(String name) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDueDate(Date date) {
        dueDate = date;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
