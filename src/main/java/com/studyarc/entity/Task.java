package com.studyarc.entity;

import java.util.Date;

public class Task {
    private String name;
    private Date duedate;
    private boolean completed;

    public Task(String name, Date duedate) {
        this.name = name;
        this.duedate = duedate;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
