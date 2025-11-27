package com.studyarc.use_case.job_postings.generate_postings;

import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;

import java.util.List;

public interface JobRepository {

    List<JobListing> getJobListings(String countryCode, KeywordList jobKeywords, String sort, int salaryMin) throws JobRepositoryException;

    class JobRepositoryException extends Exception {
        public JobRepositoryException(String message) {
            super("Error with Adzuna API, " + message);
        }
    }
}
