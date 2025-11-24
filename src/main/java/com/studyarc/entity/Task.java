package com.studyarc.entity;

public class Task {
    private String name;
    private String duedate;
    private String completionstatus;

    public Task(String name, String duedate, String status) {
        this.name = name;
        this.duedate = duedate;
        this.completionstatus = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getStatus() {
        return this.completionstatus;
    }

    public void setStatus(String status) {
        this.completionstatus = status;
    }
}
