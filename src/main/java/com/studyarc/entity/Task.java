package com.studyarc.entity;


/**
 * Task entity class, storing all the information associated with a task
 */
public class Task {
    private String name;
    private String dueDate;
    private String completionStatus;

    public Task(String name, String dueDate, String status) {
        this.name = name;
        this.dueDate = dueDate;
        this.completionStatus = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return this.completionStatus;
    }

    public void setStatus(String status) {
        this.completionStatus = status;
    }
}
