package use_case.add_reflection;

import com.studyarc.entity.ReflectionFactory;
import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.add_reflection.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AddReflectionInteractorTest {

    @Test
    void successTest() {
        final StudyPlan[] holder = new StudyPlan[1];

        AddReflectionDataAccessInterface fakeDAO = new AddReflectionDataAccessInterface() {

            @Override
            public StudyPlan getPlan(String planName) {
                return holder[0];
            }

            @Override
            public void save() {
                // nothing needed
            }
        };

        StudyPlan plan = new StudyPlan("MyPlan", new ArrayList<>(), "CS");
        holder[0] = plan;

        ReflectionFactory factory = new ReflectionFactory();

        AddReflectionOutputBoundary presenter = new AddReflectionOutputBoundary() {
            @Override
            public void prepareSuccessView(AddReflectionOutputData data) {
                assertEquals("MyPlan", data.getPlanTitle());
                assertEquals("Arrays today", data.getReflection().getContents());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        AddReflectionInteractor interactor =
                new AddReflectionInteractor(presenter, fakeDAO, factory);

        AddReflectionInputData input =
                new AddReflectionInputData("testUser", "MyPlan", "Arrays today");

        interactor.execute(input);

        // verify reflection added
        assertEquals(1, plan.getReflections().size());
        assertEquals("Arrays today", plan.getReflections().get(0).getContents());
    }

    @Test
    void emptyContentsTest() {

        // no need for a plan holder because it should fail before accessing DAO
        AddReflectionDataAccessInterface fakeDAO = new AddReflectionDataAccessInterface() {
            @Override
            public StudyPlan getPlan(String planName) { return null; }
            @Override
            public void save() {
                // nothing needed
            }
        };

        ReflectionFactory factory = new ReflectionFactory();

        AddReflectionOutputBoundary presenter = new AddReflectionOutputBoundary() {

            @Override
            public void prepareSuccessView(AddReflectionOutputData data) {
                fail("Should not reach success view for empty contents.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Reflection cannot be empty.", error);
            }
        };

        AddReflectionInteractor interactor =
                new AddReflectionInteractor(presenter, fakeDAO, factory);

        AddReflectionInputData input =
                new AddReflectionInputData("user", "MyPlan", "");

        interactor.execute(input);
    }

    @Test
    void planNotFoundTest() {

        AddReflectionDataAccessInterface fakeDAO = new AddReflectionDataAccessInterface() {
            @Override
            public StudyPlan getPlan(String planName) {
                return null;
            }

            @Override
            public void save() {
                // nothing
            }
        };

        ReflectionFactory factory = new ReflectionFactory();

        AddReflectionOutputBoundary presenter = new AddReflectionOutputBoundary() {

            @Override
            public void prepareSuccessView(AddReflectionOutputData data) {
                fail("Should not reach success view when plan is not found.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Plan not found", error);
            }
        };

        AddReflectionInteractor interactor =
                new AddReflectionInteractor(presenter, fakeDAO, factory);

        AddReflectionInputData input =
                new AddReflectionInputData("user", "UnknownPlan", "Something");

        interactor.execute(input);
    }
}