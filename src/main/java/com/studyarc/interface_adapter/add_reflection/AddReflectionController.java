package com.studyarc.interface_adapter.add_reflection;

import com.studyarc.use_case.add_reflection.AddReflectionInputBoundary;
import com.studyarc.use_case.add_reflection.AddReflectionInputData;

/**
 * The Controller for the AddReflection Use Case.
 */

public class AddReflectionController {
    private final AddReflectionInputBoundary addReflectionUseCaseInteractor;

    public AddReflectionController(AddReflectionInputBoundary addReflectionUseCaseInteractor) {
        this.addReflectionUseCaseInteractor = addReflectionUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param planName the title of the specific plan.
     * @param contents the contents of the reflection.
     */

    public void execute(String planName, String contents) {
        final AddReflectionInputData inputData = new AddReflectionInputData(
                planName, contents);
        addReflectionUseCaseInteractor.execute(inputData);
    }

}
