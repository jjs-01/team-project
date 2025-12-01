package use_case.load_milestones;

import com.studyarc.data_access.InMemoryDataUserDataAccessObject;
import com.studyarc.entity.Milestone;
import com.studyarc.entity.StudyPlan;
import com.studyarc.interface_adapter.load_milestones.LoadMilestonesPresenter;
import com.studyarc.use_case.load_milestones.*;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class LoadMilestoneInteractorTest {

    @Test
    public void testSuccess() {
        InMemoryDataUserDataAccessObject userRepository = new InMemoryDataUserDataAccessObject();
        userRepository.registerUser("Julia", "password");
        StudyPlan studyPlan = new StudyPlan("Test plan", new ArrayList<>());

        // Add test milestones to the studyPlan
        studyPlan.getMilestones().add(new Milestone("milestone 1", "XX/XX/XXXX"));
        studyPlan.getMilestones().add(new Milestone("milestone 2", "XX/XX/XXXX"));

        userRepository.getPlans().add(studyPlan);

        LoadMilestonesInputData inputData = new LoadMilestonesInputData("Test plan");

        LoadMilestonesOutputBoundary successPresenter = new LoadMilestonesOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadMilestonesOutputData outputData) {
                assertEquals(outputData.getMilestoneInfo().get(0), 3);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected");
            }
        };

        LoadMilestonesInputBoundary interactor = new LoadMilestonesInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    public void testFailure() {

    }
}
