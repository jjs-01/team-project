package com.studyarc.use_case.track_plan;

/***
 * Output Boundary for TrackPlan usecase
 */
public interface TrackPlanOutputBoundary {
    void prepareShowPlans(TrackPlanOutputData outputData);

    void prepareShowRedirect();

    void prepareShowSavingResult(String message);
}
