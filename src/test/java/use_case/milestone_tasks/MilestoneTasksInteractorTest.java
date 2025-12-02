package use_case.milestone_tasks;

import com.studyarc.data_access.InMemoryDataUserDataAccessObject;
import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.milestone_tasks.MilestoneTasksInteractor;
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
                List<Milestone> testPlanMilestones = userRepository.getPlan("Test plan").getMilestones();
                assertEquals(3, testPlanMilestones.size());
                assertEquals("milestone 3", testPlanMilestones.get(0).getTitle());
                assertEquals("03/25/2025", testPlanMilestones.get(0).getDueDate());
                assertEquals("milestone 1", testPlanMilestones.get(1).getTitle());
                assertEquals("03/28/2025", testPlanMilestones.get(1).getDueDate());
                assertEquals("milestone 2", testPlanMilestones.get(2).getTitle());
                assertEquals("03/24/2025", testPlanMilestones.get(2).getDueDate());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected");
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
        List<String[]> milestone1TaskList = new ArrayList<>();
        milestone1TaskList.add(new String[]{"Task 1", "03/29/2025", "In progress"});
        milestone1TaskList.add(new String[]{"Task 2", "04/30/2025", "Done"});

        List<String[]> milestone2TaskList = new ArrayList<>();
        milestone2TaskList.add(new String[]{"Task 3", "05/14/2025", "Not started"});

        List<String[]> milestone3TaskList = new ArrayList<>();
        milestone3TaskList.add(new String[]{"Task 4", "05/16/2025", "Not started"});
        milestone3TaskList.add(new String[]{"Task 5", "05/19/2025", "Not started"});
        milestone3TaskList.add(new String[]{"Task 6", "05/26/2025", "Not started"});

        listsOfTasksPerMilestone.add(milestone1TaskList);
        listsOfTasksPerMilestone.add(milestone2TaskList);
        listsOfTasksPerMilestone.add(milestone3TaskList);

        // now return the input data
        return new MilestoneTasksInputData("Test plan",
                milestoneNames,
                milestoneDates,
                listsOfTasksPerMilestone,
                "Artificial Intelligence");
    }
}
