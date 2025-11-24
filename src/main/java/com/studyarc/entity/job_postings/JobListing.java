package com.studyarc.entity.job_postings;

public class JobListing {
    private final String title;
    private final long jobId;
    private final String companyName;
    private final double salaryMin;
    private final double salaryMax;
    private final String jobDesc;
    private final String jobLoc;
    private final String redirectUrl;

    public JobListing(String title, long jobId, String companyName, double salaryMin, double salaryMax, String jobDesc, String jobLoc, String redirectUrl) {
        this.title = title;
        this.jobId = jobId;
        this.companyName = companyName;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.jobDesc = jobDesc;
        this.jobLoc = jobLoc;
        this.redirectUrl = redirectUrl;
    }

    public String getTitle() {
        return title;
    }
    public long getJobId() {
        return jobId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public double getSalaryMin() {
        return salaryMin;
    }
    public double getSalaryMax() {
        return salaryMax;
    }
    public String getJobDesc() {
        return jobDesc;
    }
    public String getJobLoc() {
        return jobLoc;
    }
    public String getRedirectUrl() {
        return redirectUrl;
    }
}
