package Viewing_Research_Papers;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.viewing_research_papers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ViewingResearchPapersInteractorTest {

    private TestDataAccessInterface testRepository;
    private TestOutputBoundary testPresenter;
    private ViewingResearchPapersInteractor interactor;
    private ViewingResearchPapersInputData inputData;

    // Test double for DataAccessInterface
    static class TestDataAccessInterface implements ViewingResearchPapersDataAccessInterface {
        private ArrayList<StudyPlan> plans;
        private RuntimeException exceptionToThrow;
        private int getPlansCallCount = 0;

        public void setPlans(ArrayList<StudyPlan> plans) {
            this.plans = plans;
        }

        public void setExceptionToThrow(RuntimeException exception) {
            this.exceptionToThrow = exception;
        }

        @Override
        public ArrayList<StudyPlan> getPlans() {
            getPlansCallCount++;
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return plans;
        }

        public int getGetPlansCallCount() {
            return getPlansCallCount;
        }
    }

    // Test double for OutputBoundary
    static class TestOutputBoundary implements ViewingResearchPapersOutputBoundary {
        private ViewingResearchPapersOutputData successData;
        private String failMessage;
        private int successCallCount = 0;
        private int failCallCount = 0;

        @Override
        public void prepareSuccessView(ViewingResearchPapersOutputData outputData) {
            successCallCount++;
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failCallCount++;
            this.failMessage = errorMessage;
        }

        public ViewingResearchPapersOutputData getSuccessData() {
            return successData;
        }

        public String getFailMessage() {
            return failMessage;
        }

        public int getSuccessCallCount() {
            return successCallCount;
        }

        public int getFailCallCount() {
            return failCallCount;
        }
    }

    @BeforeEach
    void setUp() {
        testRepository = new TestDataAccessInterface();
        testPresenter = new TestOutputBoundary();
        interactor = new ViewingResearchPapersInteractor(testRepository, testPresenter);
        inputData = new ViewingResearchPapersInputData();
    }

    @Test
    void testExecuteWithMultiplePlans() {
        // Arrange
        ArrayList<StudyPlan> plans = new ArrayList<>();

        StudyPlan plan1 = new StudyPlan("AI Plan", new ArrayList<>(), "Artificial Intelligence");
        ResearchPaper paper1 = new ResearchPaper(
                "1",
                "Deep Learning",
                Arrays.asList("John Doe"),
                "Abstract",
                "http://example.com",
                "10.1234/ai.001",
                2023,
                "http://example.com/paper1.pdf"
        );
        plan1.addResearchPaper(paper1);

        StudyPlan plan2 = new StudyPlan("ML Plan", new ArrayList<>(), "Machine Learning");
        ResearchPaper paper2 = new ResearchPaper(
                "2",
                "Neural Networks",
                Arrays.asList("Jane Smith"),
                "Abstract 2",
                "http://example.com/paper2",
                "10.1234/ml.001",
                2024,
                "http://example.com/paper2.pdf"
        );
        plan2.addResearchPaper(paper2);

        plans.add(plan1);
        plans.add(plan2);

        testRepository.setPlans(plans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testRepository.getGetPlansCallCount());
        assertEquals(1, testPresenter.getSuccessCallCount());
        assertEquals(0, testPresenter.getFailCallCount());

        ViewingResearchPapersOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
        assertEquals(2, outputData.getPlans().size());
        assertTrue(outputData.hasPlans());
        assertEquals("AI Plan", outputData.getPlans().get(0).getTitle());
        assertEquals("ML Plan", outputData.getPlans().get(1).getTitle());
    }

    @Test
    void testExecuteWithEmptyPlans() {
        // Arrange
        ArrayList<StudyPlan> emptyPlans = new ArrayList<>();
        testRepository.setPlans(emptyPlans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testRepository.getGetPlansCallCount());
        assertEquals(1, testPresenter.getSuccessCallCount());
        assertEquals(0, testPresenter.getFailCallCount());

        ViewingResearchPapersOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
        assertTrue(outputData.getPlans().isEmpty());
        assertFalse(outputData.hasPlans());
    }

    @Test
    void testExecuteWithSinglePlan() {
        // Arrange
        ArrayList<StudyPlan> plans = new ArrayList<>();
        StudyPlan plan = new StudyPlan("Single Plan", new ArrayList<>(), "Data Science");
        plans.add(plan);

        testRepository.setPlans(plans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testRepository.getGetPlansCallCount());
        assertEquals(1, testPresenter.getSuccessCallCount());

        ViewingResearchPapersOutputData outputData = testPresenter.getSuccessData();
        assertEquals(1, outputData.getPlans().size());
        assertTrue(outputData.hasPlans());
    }

    @Test
    void testExecuteWithRepositoryException() {
        // Arrange
        testRepository.setExceptionToThrow(new RuntimeException("Database error"));

        // Act
        try {
            interactor.execute(inputData);
        } catch (RuntimeException e) {
            // Expected exception - interactor doesn't handle it
        }

        // Assert
        assertEquals(1, testRepository.getGetPlansCallCount());
        assertEquals(0, testPresenter.getSuccessCallCount());
        assertEquals(0, testPresenter.getFailCallCount());
    }

    @Test
    void testExecuteWithNullPointerException() {
        // Arrange
        testRepository.setExceptionToThrow(new NullPointerException("Null error"));

        // Act
        try {
            interactor.execute(inputData);
        } catch (NullPointerException e) {
            // Expected exception - interactor doesn't handle it
        }

        // Assert
        assertEquals(1, testRepository.getGetPlansCallCount());
        assertEquals(0, testPresenter.getSuccessCallCount());
        assertEquals(0, testPresenter.getFailCallCount());
    }

    @Test
    void testExecuteWithPlanContainingMultiplePapers() {
        // Arrange
        ArrayList<StudyPlan> plans = new ArrayList<>();
        StudyPlan plan = new StudyPlan("Research Plan", new ArrayList<>(), "Computer Science");

        ResearchPaper paper1 = new ResearchPaper(
                "1", "Paper 1", Arrays.asList("Author 1"), "Abstract 1",
                "http://url1.com", "10.1234/1", 2023, "http://download1.com"
        );
        ResearchPaper paper2 = new ResearchPaper(
                "2", "Paper 2", Arrays.asList("Author 2"), "Abstract 2",
                "http://url2.com", "10.1234/2", 2024, "http://download2.com"
        );
        ResearchPaper paper3 = new ResearchPaper(
                "3", "Paper 3", Arrays.asList("Author 3"), "Abstract 3",
                "http://url3.com", "10.1234/3", 2024, "http://download3.com"
        );

        plan.addResearchPaper(paper1);
        plan.addResearchPaper(paper2);
        plan.addResearchPaper(paper3);
        plans.add(plan);

        testRepository.setPlans(plans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testPresenter.getSuccessCallCount());

        ViewingResearchPapersOutputData outputData = testPresenter.getSuccessData();
        assertEquals(1, outputData.getPlans().size());
        assertEquals(3, outputData.getPlans().get(0).getResearchPapers().size());
        assertTrue(outputData.hasPlans());
    }

    @Test
    void testExecuteVerifiesOutputDataCorrectness() {
        // Arrange
        ArrayList<StudyPlan> plans = new ArrayList<>();
        StudyPlan plan = new StudyPlan("Test Plan", new ArrayList<>(), "Testing");
        plans.add(plan);

        testRepository.setPlans(plans);

        // Act
        interactor.execute(inputData);

        // Assert
        ViewingResearchPapersOutputData outputData = testPresenter.getSuccessData();
        assertNotNull(outputData);
        assertNotNull(outputData.getPlans());
        assertEquals(1, outputData.getPlans().size());
        assertTrue(outputData.hasPlans());
        assertEquals("Test Plan", outputData.getPlans().get(0).getTitle());
        assertEquals("Testing", outputData.getPlans().get(0).getFocus());
    }
}