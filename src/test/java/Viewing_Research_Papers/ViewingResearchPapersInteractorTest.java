package Viewing_Research_Papers;

import com.studyarc.entity.ResearchPaper;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInteractor;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersDataAccessInterface;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputBoundary;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersInputData;
import com.studyarc.use_case.viewing_research_papers.ViewingResearchPapersOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ViewingResearchPapersInteractorTest {

    private TestDataAccess testDataAccess;
    private TestPresenter testPresenter;
    private ViewingResearchPapersInteractor interactor;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestDataAccess();
        testPresenter = new TestPresenter();
        interactor = new ViewingResearchPapersInteractor(testDataAccess, testPresenter);
    }

    @Test
    @DisplayName("Execute successfully with plans containing research papers")
    void testExecuteWithPlansSuccess() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();

        ArrayList<StudyPlan> testPlans = new ArrayList<>();
        StudyPlan plan1 = new StudyPlan("Plan 1", new ArrayList<>(), "Machine Learning");
        plan1.addResearchPaper(new ResearchPaper(
                "1",
                "Deep Learning for Computer Vision",
                Arrays.asList("Smith, J."),
                "Abstract text",
                "http://example.com/paper1",
                "10.1234/test.001",
                2023,
                "http://example.com/paper1.pdf"
        ));
        plan1.addResearchPaper(new ResearchPaper(
                "2",
                "Neural Networks",
                Arrays.asList("Johnson, A."),
                "Abstract text",
                "http://example.com/paper2",
                "10.1234/test.002",
                2023,
                "http://example.com/paper2.pdf"
        ));

        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>(), "NLP");
        plan2.addResearchPaper(new ResearchPaper(
                "3",
                "NLP with Transformers",
                Arrays.asList("Williams, B."),
                "Abstract text",
                "http://example.com/paper3",
                "10.1234/test.003",
                2023,
                "http://example.com/paper3.pdf"
        ));

        testPlans.add(plan1);
        testPlans.add(plan2);

        testDataAccess.setPlansToReturn(testPlans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount(), "Repository should be called exactly once");
        assertTrue(testPresenter.wasSuccessCalled(), "Success view should be called");
        assertFalse(testPresenter.wasFailCalled(), "Fail view should not be called");

        ViewingResearchPapersOutputData outputData = testPresenter.getOutputData();
        assertNotNull(outputData);
        assertEquals(2, outputData.getPlans().size());
        assertTrue(outputData.hasPlans());
        assertEquals("Plan 1", outputData.getPlans().get(0).getTitle());
        assertEquals(2, outputData.getPlans().get(0).getResearchPapers().size());
        assertEquals("Plan 2", outputData.getPlans().get(1).getTitle());
        assertEquals(1, outputData.getPlans().get(1).getResearchPapers().size());
    }

    @Test
    @DisplayName("Execute successfully with empty plans list")
    void testExecuteWithEmptyPlans() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();
        ArrayList<StudyPlan> emptyPlans = new ArrayList<>();
        testDataAccess.setPlansToReturn(emptyPlans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount());
        assertTrue(testPresenter.wasSuccessCalled());
        assertFalse(testPresenter.wasFailCalled());

        ViewingResearchPapersOutputData outputData = testPresenter.getOutputData();
        assertTrue(outputData.getPlans().isEmpty());
        assertFalse(outputData.hasPlans());
    }

    @Test
    @DisplayName("Execute with single plan containing no research papers")
    void testExecuteWithPlanWithoutPapers() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();

        ArrayList<StudyPlan> testPlans = new ArrayList<>();
        StudyPlan planWithoutPapers = new StudyPlan("Empty Plan", new ArrayList<>(), "AI");
        testPlans.add(planWithoutPapers);
        testDataAccess.setPlansToReturn(testPlans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount());
        assertTrue(testPresenter.wasSuccessCalled());

        ViewingResearchPapersOutputData outputData = testPresenter.getOutputData();
        assertEquals(1, outputData.getPlans().size());
        assertTrue(outputData.hasPlans());
        assertTrue(outputData.getPlans().get(0).getResearchPapers().isEmpty());
    }

    @Test
    @DisplayName("Execute handles RuntimeException from repository")
    void testExecuteWithRuntimeException() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();
        String errorMessage = "Database connection failed";
        testDataAccess.setExceptionToThrow(new RuntimeException(errorMessage));

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount());
        assertFalse(testPresenter.wasSuccessCalled());
        assertTrue(testPresenter.wasFailCalled());
        assertEquals("Failed to load research papers: " + errorMessage, testPresenter.getErrorMessage());
    }

    @Test
    @DisplayName("Execute handles NullPointerException from repository")
    void testExecuteWithNullPointerException() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();
        testDataAccess.setExceptionToThrow(new NullPointerException("Null value encountered"));

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount());
        assertTrue(testPresenter.wasFailCalled());
        assertEquals("Failed to load research papers: Null value encountered", testPresenter.getErrorMessage());
    }

    @Test
    @DisplayName("Execute handles generic Exception from repository")
    void testExecuteWithGenericException() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();
        testDataAccess.setExceptionToThrow(new IllegalStateException("Invalid state"));

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount());
        assertTrue(testPresenter.wasFailCalled());
        assertEquals("Failed to load research papers: Invalid state", testPresenter.getErrorMessage());
    }

    @Test
    @DisplayName("Execute handles exception with null message")
    void testExecuteWithExceptionNullMessage() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();
        testDataAccess.setExceptionToThrow(new RuntimeException((String) null));

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(1, testDataAccess.getCallCount());
        assertTrue(testPresenter.wasFailCalled());
        assertEquals("Failed to load research papers: null", testPresenter.getErrorMessage());
    }

    @Test
    @DisplayName("Execute with multiple plans containing varying numbers of papers")
    void testExecuteWithMultiplePlansVaryingPapers() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();

        ArrayList<StudyPlan> testPlans = new ArrayList<>();

        StudyPlan plan1 = new StudyPlan("Plan 1", new ArrayList<>(), "Focus 1");
        plan1.addResearchPaper(new ResearchPaper(
                "1",
                "Paper 1",
                Arrays.asList("Author 1"),
                "Abstract",
                "http://url1",
                "10.1234/test.001",
                2023,
                "http://url1.pdf"
        ));

        StudyPlan plan2 = new StudyPlan("Plan 2", new ArrayList<>(), "Focus 2");

        StudyPlan plan3 = new StudyPlan("Plan 3", new ArrayList<>(), "Focus 3");
        plan3.addResearchPaper(new ResearchPaper(
                "2",
                "Paper 2",
                Arrays.asList("Author 2"),
                "Abstract",
                "http://url2",
                "10.1234/test.002",
                2023,
                "http://url2.pdf"
        ));
        plan3.addResearchPaper(new ResearchPaper(
                "3",
                "Paper 3",
                Arrays.asList("Author 3"),
                "Abstract",
                "http://url3",
                "10.1234/test.003",
                2023,
                "http://url3.pdf"
        ));
        plan3.addResearchPaper(new ResearchPaper(
                "4",
                "Paper 4",
                Arrays.asList("Author 4"),
                "Abstract",
                "http://url4",
                "10.1234/test.004",
                2023,
                "http://url4.pdf"
        ));

        testPlans.add(plan1);
        testPlans.add(plan2);
        testPlans.add(plan3);
        testDataAccess.setPlansToReturn(testPlans);

        // Act
        interactor.execute(inputData);

        // Assert
        assertTrue(testPresenter.wasSuccessCalled());
        ViewingResearchPapersOutputData outputData = testPresenter.getOutputData();
        assertEquals(3, outputData.getPlans().size());
        assertTrue(outputData.hasPlans());
        assertEquals(1, outputData.getPlans().get(0).getResearchPapers().size());
        assertEquals(0, outputData.getPlans().get(1).getResearchPapers().size());
        assertEquals(3, outputData.getPlans().get(2).getResearchPapers().size());
    }

    @Test
    @DisplayName("Verify interactor correctly passes data to output data object")
    void testOutputDataCorrectness() {
        // Arrange
        ViewingResearchPapersInputData inputData = new ViewingResearchPapersInputData();

        ArrayList<StudyPlan> testPlans = new ArrayList<>();
        StudyPlan plan = new StudyPlan("Test Plan", new ArrayList<>(), "Test Focus");
        ResearchPaper paper = new ResearchPaper(
                "123",
                "Test Paper Title",
                Arrays.asList("Test Author"),
                "Test Abstract",
                "http://test.com",
                "10.1234/test.123",
                2023,
                "http://test.com/paper.pdf"
        );
        plan.addResearchPaper(paper);
        testPlans.add(plan);
        testDataAccess.setPlansToReturn(testPlans);

        // Act
        interactor.execute(inputData);

        // Assert
        ViewingResearchPapersOutputData outputData = testPresenter.getOutputData();
        StudyPlan outputPlan = outputData.getPlans().get(0);
        ResearchPaper outputPaper = outputPlan.getResearchPapers().get(0);

        assertEquals("Test Plan", outputPlan.getTitle());
        assertEquals("123", outputPaper.getId());
        assertEquals("Test Paper Title", outputPaper.getTitle());
        assertEquals("Test Author", outputPaper.getAuthorsAsString());
        assertEquals("Test Abstract", outputPaper.getAbstractText());
        assertEquals("http://test.com", outputPaper.getUrl());
    }

    // Test double for DataAccessInterface
    private static class TestDataAccess implements ViewingResearchPapersDataAccessInterface {
        private int callCount = 0;
        private ArrayList<StudyPlan> plansToReturn = new ArrayList<>();
        private Exception exceptionToThrow = null;

        @Override
        public ArrayList<StudyPlan> getPlans() {  // CHANGED: No parameter
            callCount++;
            if (exceptionToThrow != null) {
                if (exceptionToThrow instanceof RuntimeException) {
                    throw (RuntimeException) exceptionToThrow;
                } else {
                    throw new RuntimeException(exceptionToThrow);
                }
            }
            return plansToReturn;
        }

        public void setPlansToReturn(ArrayList<StudyPlan> plans) {
            this.plansToReturn = plans;
        }

        public void setExceptionToThrow(Exception exception) {
            this.exceptionToThrow = exception;
        }

        public int getCallCount() {
            return callCount;
        }
    }

    // Test double for OutputBoundary
    private static class TestPresenter implements ViewingResearchPapersOutputBoundary {
        private boolean successCalled = false;
        private boolean failCalled = false;
        private ViewingResearchPapersOutputData outputData = null;
        private String errorMessage = null;

        @Override
        public void prepareSuccessView(ViewingResearchPapersOutputData outputData) {
            this.successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failCalled = true;
            this.errorMessage = errorMessage;
        }

        public boolean wasSuccessCalled() {
            return successCalled;
        }

        public boolean wasFailCalled() {
            return failCalled;
        }

        public ViewingResearchPapersOutputData getOutputData() {
            return outputData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}