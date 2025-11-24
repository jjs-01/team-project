package com.studyarc.interface_adapter.job_postings;

/**
 * The state for the Job Postings View Model.
 */
public class JobPostingsState {
    private String focus = "";
    private String location = "";
    private String minSalary = "";
    private String sort = "";

    public String getFocus() {
        return focus;
    }

    public String getLocation() {
        return location;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public String getSort() {
        return sort;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
