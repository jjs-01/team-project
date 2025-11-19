package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

public class AddReflectionInteractor implements AddReflectionInputBoundary {
    private final AddReflectionOutputBoundary reflectionLogPresenter;
    private final AddReflectionDataAccessInterface reflectionLogDataAccess;

    public AddReflectionInteractor(AddReflectionOutputBoundary reflectionLogPresenter,
                                   AddReflectionDataAccessInterface reflectionLogDataAccess) {
        this.reflectionLogPresenter = reflectionLogPresenter;
        this.reflectionLogDataAccess = reflectionLogDataAccess;
    }

    @Override
    public void execute(AddReflectionInputData inputData) {
        final String planName = inputData.getPlanName();
        final String contents = inputData.getContents();
        if (contents.isEmpty()) {
            reflectionLogPresenter.prepareFailView("Please enter a valid contents");
        }
        else {
            User user = reflectionLogDataAccess.getCurrentUser();
            StudyPlan plan = reflectionLogDataAccess.getPlan(user, planName);
            if (plan == null) {
                reflectionLogPresenter.prepareFailView("Plan not found");
            }
            else {
                Reflection newReflection = new Reflection(contents);
                plan.addReflection(newReflection);
                reflectionLogDataAccess.savePlan(user, plan);
                AddReflectionOutputData output = new AddReflectionOutputData(contents);
                reflectionLogPresenter.prepareSuccessView(output);
            }
        }
    }
}
