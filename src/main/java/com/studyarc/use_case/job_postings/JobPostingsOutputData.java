package com.studyarc.use_case.job_postings;

import com.studyarc.entity.job_postings.JobListing;

import java.util.List;

/**
 * Output Data for the Job Postings Use Case.
 */
public class JobPostingsOutputData {

    private final List<JobListing> jobListings;

    public JobPostingsOutputData(List<JobListing> jobListings) {
        this.jobListings = jobListings;
    }

    public List<JobListing> getJobListings() {
        return jobListings;
    }
}
