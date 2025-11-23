package com.studyarc.entity;

public class Task {
    private String name;
    private String duedate;
    private String completionStatus;

    public Task(String name, String duedate, String completionStatus) {
        this.name = name;
        this.duedate = duedate;
        this.completionStatus = completionStatus;
    }
}
