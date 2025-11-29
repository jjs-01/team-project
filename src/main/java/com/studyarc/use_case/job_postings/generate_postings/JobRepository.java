package com.studyarc.use_case.job_postings.generate_postings;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;

import java.util.List;

public interface JobRepository {

    List<JobListing> getJobListings(String foucus, String countryCode, KeywordList jobKeywords, String sort, String salaryMin) throws JobRepositoryException;
    int numberResults(List<JobListing> listings);

    class JobRepositoryException extends Exception {
        public JobRepositoryException(String message) {
            super("Error with Adzuna API, " + message);
        }
    }


}
