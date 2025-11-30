package com.studyarc.use_case.milestone_tasks;

/**
 * Output boundary interface for the saving milestones use case
 */
public interface MilestoneTasksOutputBoundary {
    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(MilestoneTasksOutputData outputData);

    /**
     * Prepares the fail view for the Signup Use Case.
     * @param error the output error
     */
    void prepareFailView(String error);
}
