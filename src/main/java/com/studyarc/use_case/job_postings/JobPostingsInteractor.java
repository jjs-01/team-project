package com.studyarc.use_case.job_postings;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;
import com.studyarc.use_case.job_postings.generate_keywords.KeywordGenerator;
import com.studyarc.use_case.job_postings.generate_postings.AdzunaJobGenerator;
import com.studyarc.use_case.job_postings.generate_postings.JobRepository;

import java.util.Arrays;
import java.util.List;

/**
 * The Job Postings Interactor.
 */
public class JobPostingsInteractor implements JobPostingsInputBoundary {
    private final JobPostingsDataAccessInterface userDataAccessObject;
    private final JobPostingsOutputBoundary jobPostingsPresenter;
    private final KeywordGenerator keywordGenerator;
    private final AdzunaJobGenerator jobGenerator;


    public JobPostingsInteractor(JobPostingsDataAccessInterface userDataAccessObject, JobPostingsOutputBoundary jobPostingsPresenter, KeywordGenerator keywordGenerator, AdzunaJobGenerator jobGenerator) {
        this.userDataAccessObject = userDataAccessObject;
        this.jobPostingsPresenter = jobPostingsPresenter;
        this.keywordGenerator = keywordGenerator;
        this.jobGenerator = jobGenerator;
    }

    @Override
    public void execute(JobPostingsInputData jobPostingsInputData) {
        // getting the arguments
        String selectedFocus = jobPostingsInputData.getFocus();
        String sort = jobPostingsInputData.getSort();
        String countryCode = jobPostingsInputData.getPreferredLoc();
        String salaryMin = jobPostingsInputData.getMinSalary();

        if (selectedFocus.isEmpty() || selectedFocus.equals("Select Plan")) {
            jobPostingsPresenter.prepareFailView("You must select a focus.");

        } else {
            try {
                // generates keywords for the focus the user selected
                KeywordList keywords = keywordGenerator.generate(selectedFocus);

                // generates the job listings for the given keywords
                List<JobListing> jobListings = jobGenerator.getJobListings(selectedFocus, countryCode, keywords, sort, salaryMin);

                // creates the output data object
                final JobPostingsOutputData jobPostingsOutputData = new JobPostingsOutputData(jobListings);

                if (jobListings.isEmpty()) {
                    jobPostingsPresenter.prepareFailView("No jobs found.");
                } else {
                    // sends the success view
                    jobPostingsPresenter.prepareSuccessView(jobPostingsOutputData);
                }

            } catch (KeywordGenerator.KeywordGeneratorException | JobRepository.JobRepositoryException e ){
                System.out.println(e.getMessage() + " " + e.getCause() + " " + Arrays.toString(e.getStackTrace()));
                // sends the failed view with NEED A MESSAGE
                jobPostingsPresenter.prepareFailView("An error has occurred, please try again later.");

            }

        }

    }
}
