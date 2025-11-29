package com.studyarc.use_case.job_postings;

/**
 * The output boundary for the Job Postings Use Case.
 */
public interface JobPostingsOutputBoundary {
    /**
     * Prepares the success view for the Job Postings Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(JobPostingsOutputData outputData);

    /**
     * Prepares the failure view for the Job Postings Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
