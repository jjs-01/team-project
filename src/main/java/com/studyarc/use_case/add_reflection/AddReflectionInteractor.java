package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

public class AddReflectionInteractor implements AddReflectionInputBoundary {
    private final AddReflectionOutputBoundary addReflectionPresenter;
    private final AddReflectionDataAccessInterface addReflectionDataAccess;

    public AddReflectionInteractor(AddReflectionOutputBoundary addReflectionPresenter,
                                   AddReflectionDataAccessInterface addReflectionDataAccess) {
        this.addReflectionPresenter = addReflectionPresenter;
        this.addReflectionDataAccess = addReflectionDataAccess;
    }

    @Override
    public void execute(AddReflectionInputData inputData) {
        final String planTitle = inputData.getPlanTitle();
        final String contents = inputData.getContents();
        if (contents.isEmpty()) {
            addReflectionPresenter.prepareFailView("Please enter a valid contents");
        }
        else {
            User user = addReflectionDataAccess.getCurrentUser();
            StudyPlan plan = addReflectionDataAccess.getPlan(user, planTitle);
            if (plan == null) {
                addReflectionPresenter.prepareFailView("Plan not found");
            }
            else {
                Reflection newReflection = new Reflection(contents);
                plan.getReflections().add(newReflection);
                addReflectionDataAccess.savePlan(user, plan);
                AddReflectionOutputData output = new AddReflectionOutputData(planTitle, newReflection);
                addReflectionPresenter.prepareSuccessView(output);
            }
        }
    }
}
