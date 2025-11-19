package com.studyarc.interface_adapter.job_postings;

import com.studyarc.use_case.job_postings.JobPostingsOutputBoundary;
import com.studyarc.use_case.job_postings.JobPostingsOutputData;
import com.studyarc.view.JobPostingsView;

import java.util.ArrayList;

/**
 * The Presenter for the Job Postings Use Case.
 */
public class JobPostingsPresenter implements JobPostingsOutputBoundary {
    private final JobPostingsViewModel jobPostingsViewModel;

    public JobPostingsPresenter(JobPostingsViewModel jobPostingsViewModel) {
        this.jobPostingsViewModel = jobPostingsViewModel;
    }


    @Override
    public void prepareSuccessView(JobPostingsOutputData outputData) {
        // update the job postings view model state
        final JobPostingsState jobPostingsState = jobPostingsViewModel.getState();
        jobPostingsState.setJobListings(outputData.getJobListings());
        jobPostingsViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final JobPostingsState jobPostingsState = jobPostingsViewModel.getState();
        jobPostingsState.setListingError(errorMessage);
        jobPostingsViewModel.firePropertyChange();

    }
}
