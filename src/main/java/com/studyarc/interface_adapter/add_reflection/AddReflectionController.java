package com.studyarc.interface_adapter.add_reflection;

import com.studyarc.use_case.add_reflection.AddReflectionInputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionInputData;

public class AddReflectionController {
    private final AddReflectionInputBoundary addReflectionUseCaseInteractor;

    public AddReflectionController(AddReflectionInputBoundary addReflectionUseCaseInteractor) {
        this.addReflectionUseCaseInteractor = addReflectionUseCaseInteractor;
    }

    public void execute(String planName, String contents) {
        final AddReflectionInputData inputData = new AddReflectionInputData(
                planName, contents);
        addReflectionUseCaseInteractor.execute(inputData);
    }

}
