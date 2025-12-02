package use_case.job_postings;

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;
import com.studyarc.use_case.job_postings.*;
import com.studyarc.use_case.job_postings.generate_keywords.KeywordGenerator;
import com.studyarc.use_case.job_postings.generate_postings.AdzunaJobGenerator;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class JobPostingsInteractorTest {

    @Test
    public void testSuccess() {

        // creates a successful keyword list for the test
        KeywordGenerator mockKeywords = new KeywordGenerator() {
            @Override
            public KeywordList generate(String focus) throws KeywordGeneratorException {
                return new KeywordList("java%20developer");
            }
        };

        // creates a successful job listings for the test
        AdzunaJobGenerator mockAdzunaJobGenerator = new AdzunaJobGenerator() {
            @Override
            public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) throws JobRepositoryException {

                List<JobListing> jobs = new ArrayList<>();
                jobs.add(new JobListing("Java Developer", 12345L, "Google", 60000, 100000, "Description", "Canada", "https://www.google.com"));
                return jobs;
            }

            public int numberResults(List<JobListing> listings) {
                return listings.size();
            }
        };

        // creates a successful data input for the test case
        JobPostingsInputData jobPostingsInputData = new JobPostingsInputData("Software Developer", "", "", "");
        JobPostingsDataAccessInterface userDataAccessObj = DatabaseAccess.getInstance();

        // creates a successPresenter that tests whether the test case is as we expect
        JobPostingsOutputBoundary successPresenter =  new JobPostingsOutputBoundary() {

            @Override
            public void prepareSuccessView(JobPostingsOutputData outputData) {
                assertEquals("Java Developer", outputData.getJobListings().get(0).getTitle());
                assertEquals(1, outputData.getJobListings().size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure unexpected.");
            }

            @Override
            public void showUsersFocuses(ArrayList<String> usersFocuses) {}
        };

        JobPostingsInputBoundary interactor = new JobPostingsInteractor(userDataAccessObj, successPresenter, mockKeywords, mockAdzunaJobGenerator);
        interactor.execute(jobPostingsInputData);

    }

    @Test
    public void testFailEmptyFocus() {

        KeywordGenerator mockKeywords = new KeywordGenerator() {
            @Override
            public KeywordList generate(String focus) throws KeywordGeneratorException {
                return new KeywordList("java%20developer");
            }
        };

        AdzunaJobGenerator mockAdzunaJobGenerator = new AdzunaJobGenerator() {
            @Override
            public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) throws JobRepositoryException  {

                List<JobListing> jobs = new ArrayList<>();
                jobs.add(new JobListing("Java Developer", 12345L, "Google", 60000, 100000, "Description", "Canada", "https://www.google.com"));
                return jobs;
            }

            public int numberResults(List<JobListing> listings) {
                return listings.size();
            }
        };

        // creates a faulty input data with an empty focus input
        JobPostingsInputData jobPostingsInputData = new JobPostingsInputData("", "", "", "");
        JobPostingsDataAccessInterface userDataAccessObj = DatabaseAccess.getInstance();

        JobPostingsOutputBoundary successPresenter =  new JobPostingsOutputBoundary() {

            @Override
            public void prepareSuccessView(JobPostingsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You must select a focus.", errorMessage);
            }

            @Override
            public void showUsersFocuses(ArrayList<String> usersFocuses) {}
        };

        JobPostingsInputBoundary interactor = new JobPostingsInteractor(userDataAccessObj, successPresenter, mockKeywords, mockAdzunaJobGenerator);
        interactor.execute(jobPostingsInputData);

    }

    @Test
    public void testFailNoJobs() {

        KeywordGenerator mockKeywords = new KeywordGenerator() {
            @Override
            public KeywordList generate(String focus) throws KeywordGeneratorException {
                return new KeywordList("java%20developer");
            }
        };

        // creates an empty job listing to test the failure
        AdzunaJobGenerator mockAdzunaJobGenerator = new AdzunaJobGenerator() {
            @Override
            public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) throws JobRepositoryException {

                List<JobListing> jobs = new ArrayList<>();
                return jobs;
            }

            public int numberResults(List<JobListing> listings) {
                return listings.size();
            }
        };

        JobPostingsInputData jobPostingsInputData = new JobPostingsInputData("Software Developer", "", "", "");
        JobPostingsDataAccessInterface userDataAccessObj = DatabaseAccess.getInstance();

        JobPostingsOutputBoundary successPresenter =  new JobPostingsOutputBoundary() {

            @Override
            public void prepareSuccessView(JobPostingsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No jobs found.", errorMessage);
            }

            @Override
            public void showUsersFocuses(ArrayList<String> usersFocuses) {}
        };

        JobPostingsInputBoundary interactor = new JobPostingsInteractor(userDataAccessObj, successPresenter, mockKeywords, mockAdzunaJobGenerator);
        interactor.execute(jobPostingsInputData);

    }

    @Test
    public void testFailKeywordException() {

        // throws an exception to test the failure
        KeywordGenerator mockKeywords = new KeywordGenerator() {
            @Override
            public KeywordList generate(String focus) throws KeywordGeneratorException {
                throw new KeywordGeneratorException("error test");
            }
        };

        AdzunaJobGenerator mockAdzunaJobGenerator = new AdzunaJobGenerator() {
            @Override
            public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) throws JobRepositoryException {

                List<JobListing> jobs = new ArrayList<>();
                return jobs;
            }

            public int numberResults(List<JobListing> listings) {
                return listings.size();
            }
        };

        JobPostingsInputData jobPostingsInputData = new JobPostingsInputData("Software Developer", "", "", "");
        JobPostingsDataAccessInterface userDataAccessObj = DatabaseAccess.getInstance();

        JobPostingsOutputBoundary successPresenter =  new JobPostingsOutputBoundary() {

            @Override
            public void prepareSuccessView(JobPostingsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("An error has occurred, please try again later.", errorMessage);
            }

            @Override
            public void showUsersFocuses(ArrayList<String> usersFocuses) {}
        };

        JobPostingsInputBoundary interactor = new JobPostingsInteractor(userDataAccessObj, successPresenter, mockKeywords, mockAdzunaJobGenerator);
        interactor.execute(jobPostingsInputData);

    }

    @Test
    public void testFailAdzunaJobGeneratorException() {

        KeywordGenerator mockKeywords = new KeywordGenerator() {
            @Override
            public KeywordList generate(String focus) throws KeywordGeneratorException {
                return new KeywordList("java%20developer");
            }
        };

        // throws an exception to test the failure
        AdzunaJobGenerator mockAdzunaJobGenerator = new AdzunaJobGenerator() {
            @Override
            public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) throws JobRepositoryException {
                throw new JobRepositoryException("error test");
            }

            public int numberResults(List<JobListing> listings) {
                return listings.size();
            }
        };

        JobPostingsInputData jobPostingsInputData = new JobPostingsInputData("Software Developer", "", "", "");
        JobPostingsDataAccessInterface userDataAccessObj = DatabaseAccess.getInstance();

        JobPostingsOutputBoundary successPresenter =  new JobPostingsOutputBoundary() {

            @Override
            public void prepareSuccessView(JobPostingsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("An error has occurred, please try again later.", errorMessage);
            }

            @Override
            public void showUsersFocuses(ArrayList<String> usersFocuses) {}
        };

        JobPostingsInputBoundary interactor = new JobPostingsInteractor(userDataAccessObj, successPresenter, mockKeywords, mockAdzunaJobGenerator);
        interactor.execute(jobPostingsInputData);

    }
}
