package com.studyarc.use_case.load_milestones;

/**
 * Output boundary interaface for the load milestones use case
 */
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