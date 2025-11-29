package job_postings;

import com.studyarc.data_access.DatabaseAccess;
import com.studyarc.entity.job_postings.JobListing;
import com.studyarc.entity.job_postings.KeywordList;
import com.studyarc.use_case.job_postings.*;
import com.studyarc.use_case.job_postings.generate_keywords.KeywordGenerator;
import com.studyarc.use_case.job_postings.generate_postings.AdzunaJobGenerator;
import org.jetbrains.annotations.TestOnly;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class JobPostingsInteractorTest {

    @Test
    public void testSuccess() {

        KeywordGenerator mockKeywords = new KeywordGenerator() {
            @Override
            public KeywordList generate(String focus) throws KeywordGeneratorException {
                return new KeywordList("java%20developer");
            }
        };

        AdzunaJobGenerator mockAdzunaJobGenerator = new AdzunaJobGenerator() {
            @Override
            public List<JobListing> getJobListings(String focus, String countryCode, KeywordList keywords, String sort, String salaryMin) {

                List<JobListing> jobs = new ArrayList<>();
                jobs.add(new JobListing("Java Developer", 12345L, "Google", 60000, 100000, "Description", "Canada", "https://www.google.com"));
                return jobs;
            }

            public int numberResults(List<JobListing> listings) {
                return listings.size();
            }
        };

        JobPostingsInputData jobPostingsInputData = new JobPostingsInputData("Software Developer", "", "", "");
        JobPostingsDataAccessInterface userDataAccessObj = new DatabaseAccess();

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
            public void showUsersFocuses(ArrayList<String> usersFocuses) {

            }
        };

        JobPostingsInputBoundary interactor = new JobPostingsInteractor(userDataAccessObj, successPresenter, mockKeywords, mockAdzunaJobGenerator);
        interactor.execute(jobPostingsInputData);

    }
}
