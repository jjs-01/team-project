package com.studyarc.use_case.track_plan;

/***
 * Input Boundary for TrackPlan usecase
 */


public interface TrackPlanInputBoundary {
    //execute the login usecase
    void execute(TrackPlanInputData trackPlanInputData);

    void execute(TrackPlanSavingInputData savingdata);
}
