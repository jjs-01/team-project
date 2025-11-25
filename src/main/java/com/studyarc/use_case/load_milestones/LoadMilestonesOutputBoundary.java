package com.studyarc.use_case.load_milestones;

public interface LoadMilestonesOutputBoundary {
    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LoadMilestonesOutputData outputData);

    /**
     * Prepares the fail view for the Signup Use Case.
     * @param error the output error
     */
    void prepareFailView(String error);
}