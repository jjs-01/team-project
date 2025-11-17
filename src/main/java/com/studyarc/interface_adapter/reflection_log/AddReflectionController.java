package com.studyarc.interface_adapter.reflection_log;

import com.studyarc.use_case.add_reflection.AddReflectionInputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionInputData;
import java.time.LocalDate;

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
