package trackplan;


import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

import com.studyarc.use_case.track_plan.*;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrackPlanInteractorTest {
    private final StudyPlan testplan1 = new StudyPlan("plan1", new ArrayList<>(), "focus1");
    private final StudyPlan testPlan2 = new StudyPlan("plan2", new ArrayList<>(), "focus2");
    private final StudyPlan emptyPlanName = new StudyPlan("", new ArrayList<>(), "focus");

    @Test
    public void TestForNonEmptyStudyPlans() throws NoSuchAlgorithmException {
        TrackPlanInputData inputData = new TrackPlanInputData("peeko");
        User u = new User("peeko", "123");
        ArrayList<StudyPlan> plans = new ArrayList<>();
        plans.add(testplan1);
        plans.add(testPlan2);
        u.setStudyPlans(plans);

        TrackPlanOutputBoundary presenter = new TrackPlanOutputBoundary() {
            @Override
            public void prepareShowPlans(TrackPlanOutputData outputData) {
                assertEquals(outputData.getListOfPlan(), plans);
            }

            @Override
            public void prepareShowRedirect() {
            }

            @Override
            public void prepareShowSavingResult(String message) {
            }
        };
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
        TrackPlanInteractor interactor = new TrackPlanInteractor(presenter, Dao);
        interactor.execute(inputData);


    }

    @Test
    public void TestForEmptyStudyPlans() throws NoSuchAlgorithmException {
        TrackPlanInputData inputData = new TrackPlanInputData("peeko");
        User user = new User("peeko", "123");
        TrackPlanOutputBoundary presenter = new TrackPlanOutputBoundary() {

            @Override
            public void prepareShowPlans(TrackPlanOutputData outputData) {
                assertEquals(outputData.getListOfPlan(), user.getStudyPlans());
            }

            @Override
            public void prepareShowRedirect() {

            }

            @Override
            public void prepareShowSavingResult(String message) {

            }
        };

        TrackPlanDataAccessInterface dao = new TrackPlanDataAccessInterface() {
            @Override
            public List<StudyPlan> getPlans() {
                return user.getStudyPlans();
            }

            @Override
            public void save() {

            }

            @Override
            public void saveAllPlansForUser(List<StudyPlan> plans) {

            }
        };

        TrackPlanInteractor interactor = new TrackPlanInteractor(presenter, dao);
        interactor.execute(inputData);
    }

    @Test
    public void TestSavingEmptyPlanName() throws NoSuchAlgorithmException {
        User user = new User("peeko", "123");
        ArrayList<StudyPlan> plans = new ArrayList<>();
        plans.add(this.testPlan2);
        plans.add(this.emptyPlanName);
        user.setStudyPlans(plans);
        TrackPlanSavingInputData inputData = new TrackPlanSavingInputData(plans,"peeko");

        TrackPlanOutputBoundary presenter = new TrackPlanOutputBoundary() {
            @Override
            public void prepareShowPlans(TrackPlanOutputData outputData) {

            }

            @Override
            public void prepareShowRedirect() {

            }

            @Override
            public void prepareShowSavingResult(String message) {
                assertEquals(" Empty Plan Title! Not allowed!😡😡 ", message);
            }
        };

        TrackPlanDataAccessInterface dao = new TrackPlanDataAccessInterface() {
            @Override
            public List<StudyPlan> getPlans() {
                return null;
            }

            @Override
            public void save() {

            }

            @Override
            public void saveAllPlansForUser(List<StudyPlan> plans) {

            }
        };

        TrackPlanInteractor interactor = new TrackPlanInteractor(presenter,dao);
        interactor.execute(inputData);
    }

    @Test
    public void TestSuccessSaving() throws NoSuchAlgorithmException {
        User user = new User("peeko", "123");
        ArrayList<StudyPlan> plans = new ArrayList<>();
        plans.add(this.testPlan2);
        plans.add(this.testplan1);
        user.setStudyPlans(plans);

        TrackPlanSavingInputData inputdata = new TrackPlanSavingInputData(plans, "peeko");
        TrackPlanOutputBoundary presenter = new TrackPlanOutputBoundary() {
            @Override
            public void prepareShowPlans(TrackPlanOutputData outputData) {

            }

            @Override
            public void prepareShowRedirect() {

            }

            @Override
            public void prepareShowSavingResult(String message) {
                assertEquals(" Save complete! ", message);
            }
        };
        TrackPlanDataAccessInterface dao = new TrackPlanDataAccessInterface() {
            @Override
            public List<StudyPlan> getPlans() {
                return null;
            }

            @Override
            public void save() {

            }

            @Override
            public void saveAllPlansForUser(List<StudyPlan> plans) {

            }
        };

        TrackPlanInteractor interactor = new TrackPlanInteractor(presenter, dao);
        interactor.execute(inputdata);
    }

    @Test
    public void TestSavingWithRepetitivePlanName() throws NoSuchAlgorithmException {
        User user = new User("peeko", "123");
        ArrayList<StudyPlan> plans = new ArrayList<>();
        plans.add(this.testplan1);
        plans.add(this.testplan1);
        user.setStudyPlans(plans);
        TrackPlanSavingInputData input = new TrackPlanSavingInputData(plans, "peeko");
        TrackPlanDataAccessInterface dao = new TrackPlanDataAccessInterface() {
            @Override
            public List<StudyPlan> getPlans() {
                return null;
            }

            @Override
            public void save() {

            }

            @Override
            public void saveAllPlansForUser(List<StudyPlan> plans) {

            }
        };

        TrackPlanOutputBoundary presenter = new TrackPlanOutputBoundary() {
            @Override
            public void prepareShowPlans(TrackPlanOutputData outputData) {

            }

            @Override
            public void prepareShowRedirect() {

            }

            @Override
            public void prepareShowSavingResult(String message) {
                assertEquals(" Oops!!Can not have repetitive plans! ", message);
            }
        };

        TrackPlanInteractor interactor = new TrackPlanInteractor(presenter, dao);
        interactor.execute(input);
    }
}
