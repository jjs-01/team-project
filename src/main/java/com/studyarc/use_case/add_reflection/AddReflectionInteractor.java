package com.studyarc.use_case.add_reflection;

import com.studyarc.entity.Reflection;
import com.studyarc.entity.ReflectionFactory;
import com.studyarc.entity.StudyPlan;

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
        final String planTitle = inputData.getPlanTitle();
        final String contents = inputData.getContents();
        if (contents.isEmpty()) {
            addReflectionPresenter.prepareFailView("Reflection cannot be empty.");
        }
        else {
            final StudyPlan plan = addReflectionDataAccess.getPlan(planTitle);
            if (plan == null) {
                addReflectionPresenter.prepareFailView("Plan not found");
            }
            else {
                final Reflection newReflection = reflectionFactory.create(contents);
                plan.getReflections().add(newReflection);
                addReflectionDataAccess.savePlan(plan);
                final AddReflectionOutputData output = new AddReflectionOutputData(planTitle, newReflection);
                addReflectionPresenter.prepareSuccessView(output);
            }
        }
    }
}
