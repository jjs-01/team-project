package com.studyarc.interface_adapter.job_postings;

import com.studyarc.entity.job_postings.JobListing;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Job Postings View Model.
 */
public class JobPostingsState {
    private String focus = "";
    private String location = "";
    private String minSalary = "";
    private String sort = "";
    private List<JobListing> jobListings = new ArrayList<>();
    private String listingError = "";
    private String numberOfResults = "0";

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

    public List<JobListing> getJobListings() {
        return jobListings;
    }

    public String getListingError() {
        return listingError;
    }

    public String getNumberOfResults() { return numberOfResults; }

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

    public void setJobListings(List<JobListing> jobListings) {
        this.jobListings = jobListings;
    }

    public void setListingError(String listingError) {
        this.listingError = listingError;
    }

    public void setNumberOfResults(String numberOfResults) { this.numberOfResults = numberOfResults; }

}
