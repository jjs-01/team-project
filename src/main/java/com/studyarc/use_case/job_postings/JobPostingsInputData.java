package com.studyarc.use_case.job_postings;

/**
 * The Input Data for the Job Postings Use Case.
 */
public class JobPostingsInputData {
    private final String focus;
    private final String preferredLoc;
    private final String minSalary;
    private final String sort;

    public JobPostingsInputData(String focus, String preferredLoc, String minSalary, String sort) {
        this.focus = focus;
        this.preferredLoc = preferredLoc;
        this.minSalary = minSalary;
        this.sort = sort;
    }

    String getFocus() {
        return focus;
    }

    String getPreferredLoc() {
        return preferredLoc;
    }

    String getMinSalary() {
        return minSalary;
    }

    String getSort() {
        return sort;
    }
}
