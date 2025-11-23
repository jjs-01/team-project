package com.studyarc.entity;

import java.util.ArrayList;
import java.util.Date;

public class Milestone {
    private String title;
    private ArrayList<Task> tasks;
    private Date duedate;

    public Milestone(String title) {
        this.title = title;
        this.tasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
