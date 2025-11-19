package com.studyarc.use_case.job_postings;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;
import com.studyarc.use_case.job_postings.generate_keywords.KeywordGenerator;
import com.studyarc.use_case.job_postings.generate_postings.AdzunaJobGenerator;
import com.studyarc.use_case.job_postings.generate_postings.JobRepository;

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
        final String selectedFocus = jobPostingsInputData.getFocus();
        final String sort =  jobPostingsInputData.getSort();
        // default arguments
        String countryCode = "ca";
        int salaryMin = 0;

        // strip the format of the salary selection
        if (!jobPostingsInputData.getMinSalary().isEmpty()) {
            salaryMin = Integer.parseInt(jobPostingsInputData.getMinSalary().replace("$", "").replace(",", ""));
        }

        // set the preferred country location if selected
        if (!jobPostingsInputData.getPreferredLoc().isEmpty()) countryCode = jobPostingsInputData.getPreferredLoc();

        System.out.println("Selected Minimum: " + salaryMin);
        System.out.println("Selected Country: " + countryCode);
        System.out.println("Selected Focus: " + selectedFocus);
        System.out.println("Selected Sort: " + sort);

        if (selectedFocus.isEmpty()) {
            jobPostingsPresenter.prepareFailView("You must select a focus.");
        } else {
            try {
                // generates keywords for the focus the user selected
                KeywordList keywords = keywordGenerator.generate(selectedFocus);

                // generates the job listings for the given keywords
                List<JobListing> jobListings = jobGenerator.getJobListings(countryCode, keywords, sort, salaryMin);

                // creates the output data object
                final JobPostingsOutputData jobPostingsOutputData = new JobPostingsOutputData(jobListings);

                // sends the success view
                jobPostingsPresenter.prepareSuccessView(jobPostingsOutputData);
            } catch (KeywordGenerator.KeywordGeneratorException | JobRepository.JobRepositoryException e ){

                // sends the failed view with NEED A MESSAGE
                jobPostingsPresenter.prepareFailView("An error has occurred, please try again later.");
            }

        }

    }
}
