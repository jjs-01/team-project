package use_case.milestone_tasks;

import com.studyarc.data_access.InMemoryDataUserDataAccessObject;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.milestone_tasks.*;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksInteractorTest {
    @Test
    public void successTest() {
        // need to register a user first (not a part of the MilestoneTasksDataAccess Interface
        InMemoryDataUserDataAccessObject userRepository = new InMemoryDataUserDataAccessObject();
        userRepository.registerUser("Julia", "password");

        // creates a test studyPlan to save to
        StudyPlan studyPlan = new StudyPlan("Test plan", new ArrayList<>());
        userRepository.getPlans().add(studyPlan);

        MilestoneTasksInputData inputData = getInputObject("milestone 3", "milestone 1", "milestone 2");

        MilestoneTasksOutputBoundary successPresenter = new MilestoneTasksOutputBoundary() {
            @Override
            public void prepareSuccessView(MilestoneTasksOutputData outputData) {
                // want to test that the getStudyPlan size is 3
                assertEquals(3, userRepository.getPlan("Test plan").getMilestones().size());
                assertEquals("milestone 3", userRepository.getPlan("Test plan").getMilestones()
                        .get(0).getTitle());
                assertEquals("milestone 1", userRepository.getPlan("Test plan").getMilestones()
                        .get(1).getTitle());
                assertEquals("milestone 2", userRepository.getPlan("Test plan").getMilestones()
                        .get(2).getTitle());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is unexpected");
            }
        };

        MilestoneTasksInputBoundary interactor = new MilestoneTasksInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureSameNameTest() {
        InMemoryDataUserDataAccessObject userRepository = new InMemoryDataUserDataAccessObject();
        userRepository.registerUser("Julia", "password");


        // creates a test studyPlan to save to
        StudyPlan studyPlan = new StudyPlan("Test plan", new ArrayList<>());
        userRepository.getPlans().add(studyPlan);

        MilestoneTasksInputData inputData = getInputObject("milestone 3", "duplicate milestone", "duplicate milestone");

        MilestoneTasksOutputBoundary failurePresenter = new MilestoneTasksOutputBoundary() {
            @Override
            public void prepareSuccessView(MilestoneTasksOutputData outputData) {
                fail("Use case success is unexpected");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Can't have more than one milestone with the same name", error);
            }
        };

        MilestoneTasksInputBoundary interactor = new MilestoneTasksInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureEmptyNameTest() {
        InMemoryDataUserDataAccessObject userRepository = new InMemoryDataUserDataAccessObject();
        userRepository.registerUser("Julia", "password");

        // creates a test studyPlan to save to
        StudyPlan studyPlan = new StudyPlan("Test plan", new ArrayList<>());
        userRepository.getPlans().add(studyPlan);

        MilestoneTasksInputData inputData = getInputObject("Test milestone", "", "milestone 2");

        MilestoneTasksOutputBoundary failurePresenter = new MilestoneTasksOutputBoundary() {
            @Override
            public void prepareSuccessView(MilestoneTasksOutputData outputData) {
                fail("Use case success is unexpected");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Can't save a study plan with an empty title", error);
            }
        };

        MilestoneTasksInputBoundary interactor = new MilestoneTasksInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @NotNull
    private static MilestoneTasksInputData getInputObject(String name1, String name2, String name3) {
        List<String> milestoneNames = new ArrayList<>();
        milestoneNames.add(name1);
        milestoneNames.add(name2);
        milestoneNames.add(name3);

        List<String> milestoneDates = new ArrayList<>();
        milestoneDates.add("03/25/2025");
        milestoneDates.add("03/28/2025");
        milestoneDates.add("03/24/2025");

        List<List<String[]>> listsOfTasksPerMilestone = new ArrayList<>();
        listsOfTasksPerMilestone.add(new ArrayList<>());
        listsOfTasksPerMilestone.add(new ArrayList<>());
        listsOfTasksPerMilestone.add(new ArrayList<>());

        // now return the input data
        return new MilestoneTasksInputData("Test plan",
                milestoneNames,
                milestoneDates,
                listsOfTasksPerMilestone,
                "Artificial Intelligence");
    }
}
