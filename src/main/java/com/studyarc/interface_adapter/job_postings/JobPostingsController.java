package com.studyarc.interface_adapter.job_postings;

import com.studyarc.use_case.job_postings.JobPostingsInputBoundary;
import com.studyarc.use_case.job_postings.JobPostingsInputData;

/**
 * The controller for the Job Postings Use Case.
 */
public class JobPostingsController {
    private final JobPostingsInputBoundary jobPostingsUseCaseInteractor;

    public JobPostingsController(JobPostingsInputBoundary jobPostingsUseCaseInteractor) {
        this.jobPostingsUseCaseInteractor = jobPostingsUseCaseInteractor;
    }

    public void execute(String focus, String preferredLoc, String minSalary, String sort) {
        final JobPostingsInputData jobPostingsInputData = new JobPostingsInputData(focus, preferredLoc, minSalary, sort);

        jobPostingsUseCaseInteractor.execute(jobPostingsInputData);

    }
}
