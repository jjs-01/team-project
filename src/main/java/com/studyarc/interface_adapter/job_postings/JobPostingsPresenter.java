package com.studyarc.interface_adapter.job_postings;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.use_case.job_postings.JobPostingsOutputBoundary;
import com.studyarc.use_case.job_postings.JobPostingsOutputData;
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
        jobPostingsState.setNumberOfResults(String.valueOf(outputData.getNumberOfResults()));

        // formatting the range
        formattingRange(jobPostingsState);

        jobPostingsViewModel.firePropertyChange();
    }

    private static void formattingRange(JobPostingsState jobPostingsState) {
        for (JobListing jobListing : jobPostingsState.getJobListings()) {
            String formatMin = String.valueOf((int)jobListing.getSalaryMin());
            String formatMax = String.valueOf((int)jobListing.getSalaryMax());

            String firstDigits;
            String restDigits;

            if (formatMin.length() == 6) {
                    firstDigits = formatMin.substring(0, 3);
                    restDigits = formatMin.substring(3);

                    formatMin = "$" + firstDigits + "," + restDigits;
                } else if (formatMin.length() == 5) {
                    firstDigits = formatMin.substring(0, 2);
                    restDigits = formatMin.substring(2);

                    formatMin = "$" + firstDigits + "," + restDigits;
                } else {
                    formatMin = "$" + formatMin;
                }

                if (formatMax.length() == 6) {
                    firstDigits = formatMax.substring(0, 3);
                    restDigits = formatMax.substring(3);

                    formatMax = "$" + firstDigits + "," + restDigits;
                } else if (formatMax.length() == 5) {
                    firstDigits = formatMax.substring(0, 2);
                    restDigits = formatMax.substring(2);

                    formatMax = "$" + firstDigits + "," + restDigits;
                } else {
                    formatMax = "$" + formatMax;
                }

            jobListing.setFormattedMin(formatMin);
            jobListing.setFormattedMax(formatMax);

        }
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final JobPostingsState jobPostingsState = jobPostingsViewModel.getState();
        jobPostingsState.setListingError(errorMessage);
        jobPostingsViewModel.firePropertyChange();

    }
}
