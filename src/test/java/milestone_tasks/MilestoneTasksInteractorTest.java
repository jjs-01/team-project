package milestone_tasks;

import com.studyarc.data_access.InMemoryDataUserDataAccessObject;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.Milestone;
import com.studyarc.use_case.milestone_tasks.*;
import com.studyarc.data_access.DatabaseAccess;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTasksInteractorTest {
    @Test
    public void successTest() {
        // MilestoneTasksInputData inputData = new MilestoneTasksInputData("Study Plan 1");

    }

    @Test
    public void failureSameNameTest() {

    }

    @Test
    public void failureEmptyNameTest() {

    }

    @Test
    public void failureNoAvailablePlanTest() {
        MilestoneTasksInputData inputData = new MilestoneTasksInputData("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "");
        MilestoneTasksDataAccessInterface dataAccessObject = new InMemoryDataUserDataAccessObject();


        MilestoneTasksOutputBoundary failurePresenter = new MilestoneTasksOutputBoundary() {
            @Override
            public void prepareSuccessView(MilestoneTasksOutputData outputData) {
                    fail("Use case success is unexpected");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Failed to find plan. Couldn't save", error);
            }
        };

        MilestoneTasksInputBoundary interactor = new MilestoneTasksInteractor(dataAccessObject, failurePresenter);
        interactor.execute(inputData);
    }
}
