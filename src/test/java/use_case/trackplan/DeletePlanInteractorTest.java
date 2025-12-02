package use_case.trackplan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;
import com.studyarc.use_case.delete_plan.DeletePlanInputData;
import com.studyarc.use_case.delete_plan.DeletePlanInteractor;
import com.studyarc.use_case.delete_plan.DeletePlanOutputBoundary;
import com.studyarc.use_case.delete_plan.DeletePlanOutputData;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessInterface;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeletePlanInteractorTest {

    @Test
    public void TestSuccess() throws NoSuchAlgorithmException {
        User u  = new User("peeko", "123");
        StudyPlan testplan1 = new StudyPlan("plan1", new ArrayList<>(), "focus1");
        StudyPlan testPlan2 = new StudyPlan("plan2", new ArrayList<>(), "focus2");
        ArrayList<StudyPlan> plans = new ArrayList<>();
        plans.add(testplan1);
        plans.add(testPlan2);
        u.setStudyPlans(plans);
        TrackPlanDataAccessInterface Dao = new TrackPlanDataAccessInterface() {
            @Override
            public List<StudyPlan> getPlans() {
                return plans;
            }

            @Override
            public void save() {
            }

            @Override
            public void saveAllPlansForUser(List<StudyPlan> plans) {
            }
        };
        DeletePlanInputData input = new DeletePlanInputData(testplan1);
        DeletePlanOutputBoundary presenter = new DeletePlanOutputBoundary() {
            @Override
            public void showPlans(DeletePlanOutputData outputData) {
                assertEquals(testplan1 ,outputData.getPlan());
            }
        };
        DeletePlanInteractor interactor = new DeletePlanInteractor(presenter, Dao);
        interactor.execute(input);
    }
}
