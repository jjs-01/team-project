package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.ReflectionFactory;
import com.studyarc.entity.StudyPlan;
import com.studyarc.entity.User;

public class AddReflectionInteractor implements AddReflectionInputBoundary {
    private final AddReflectionOutputBoundary addReflectionPresenter;
    private final AddReflectionDataAccessInterface addReflectionDataAccess;
    private final ReflectionFactory reflectionFactory;

    public AddReflectionInteractor(AddReflectionOutputBoundary addReflectionPresenter,
                                   AddReflectionDataAccessInterface addReflectionDataAccess,
                                   ReflectionFactory reflectionFactory) {
        this.addReflectionPresenter = addReflectionPresenter;
        this.addReflectionDataAccess = addReflectionDataAccess;
        this.reflectionFactory = reflectionFactory;
    }

    @Override
    public void execute(AddReflectionInputData inputData) {
        final String username = inputData.getUsername();
        final String planTitle = inputData.getPlanTitle();
        final String contents = inputData.getContents();
        if (contents.isEmpty()) {
            addReflectionPresenter.prepareFailView("Reflection cannot be empty.");
        }
        else {
            final User user = addReflectionDataAccess.getUser(username);
            final StudyPlan plan = addReflectionDataAccess.getPlan(user, planTitle);
            if (plan == null) {
                addReflectionPresenter.prepareFailView("Plan not found");
            }
            else {
                final Reflection newReflection = reflectionFactory.create(contents);
                plan.getReflections().add(newReflection);
                addReflectionDataAccess.savePlan(user, plan);
                final AddReflectionOutputData output = new AddReflectionOutputData(planTitle, newReflection);
                addReflectionPresenter.prepareSuccessView(output);
            }
        }
    }
}
