package use_case.add_plan;

import com.studyarc.data_access.InMemoryDataUserDataAccessObject;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.add_plan.AddPlanInteractor;
import com.studyarc.use_case.add_plan.AddPlanInputData;
import com.studyarc.use_case.add_plan.AddPlanOutputBoundary;
import com.studyarc.use_case.add_plan.AddPlanOutputData;
import com.studyarc.use_case.add_plan.AddPlanInputBoundary;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class AddPlanInteractorTest {
    @Test
    public void testSuccess() {
        InMemoryDataUserDataAccessObject dataAccess = new InMemoryDataUserDataAccessObject();
        dataAccess.registerUser("Julia", "password");

        List<String> planTitles = makeExampleListOfPlans(dataAccess);

        AddPlanInputData inputData = new AddPlanInputData(planTitles);

        AddPlanOutputBoundary successPresenter = new AddPlanOutputBoundary() {
            @Override
            public void prepareSuccessView(AddPlanOutputData outputData) {
                assertEquals("Untitled Plan 3", outputData.getPlan().getTitle());
                assertEquals("Untitled Plan 3", dataAccess.getPlans().get(5).getTitle());
                assertEquals(0, dataAccess.getPlans().get(5).getMilestones().size());
            }
        };

        AddPlanInputBoundary interactor = new AddPlanInteractor(dataAccess, successPresenter);
        interactor.execute(inputData);
    }

    private List<String> makeExampleListOfPlans(InMemoryDataUserDataAccessObject dataAccess) {
        List<String> planNames = new ArrayList<>();
        StudyPlan studyPlan1 = new StudyPlan("Untitled Plan 1", new ArrayList<>());
        planNames.add("Untitled Plan 0");

        StudyPlan studyPlan2 = new StudyPlan("Untitled Plan 0", new ArrayList<>());
        planNames.add("Untitled Plan 2");

        StudyPlan studyPlan3 = new StudyPlan("Untitled Plan 2", new ArrayList<>());
        planNames.add("Untitled Plan 1");

        StudyPlan studyPlan4 = new StudyPlan("Test plan name", new ArrayList<>());
        planNames.add("Test plan name");

        StudyPlan studyPlan5 = new StudyPlan("Untitled Plan 6", new ArrayList<>());
        planNames.add("Untitled Plan 6");

        dataAccess.getPlans().add(studyPlan1);
        dataAccess.getPlans().add(studyPlan2);
        dataAccess.getPlans().add(studyPlan3);
        dataAccess.getPlans().add(studyPlan4);
        dataAccess.getPlans().add(studyPlan5);

        return planNames;
    }
}
