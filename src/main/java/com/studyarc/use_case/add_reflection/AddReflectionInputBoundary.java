package com.studyarc.use_case.add_reflection;

/**
 * The input boundary for the AddReflection Case.
 */
public interface AddReflectionInputBoundary {
    /**
     * Execute the Add Reflection Use Case.
     * @param inputData the input data for this Use Case.
     */
    void execute(AddReflectionInputData inputData);
}
