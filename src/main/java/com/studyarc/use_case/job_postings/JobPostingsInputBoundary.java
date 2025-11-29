package com.studyarc.use_case.job_postings;

/**
 * Input Boundary for actions which are related to job postings.
 */
public interface JobPostingsInputBoundary {

    /**
     * Executes the job postings use case.
     * @param jobPostingsInputData the input data
     */
    void execute(JobPostingsInputData jobPostingsInputData);
}
