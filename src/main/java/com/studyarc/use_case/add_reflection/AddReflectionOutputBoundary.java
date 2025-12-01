package com.studyarc.use_case.add_reflection;

/**
 * The output boundary for the Add Reflection Use Case.
 */
public interface AddReflectionOutputBoundary {
    /**
     * Prepares the success view for the Change Password Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddReflectionOutputData outputData);

    /**
     * Prepares the failure view for the Change Password Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
