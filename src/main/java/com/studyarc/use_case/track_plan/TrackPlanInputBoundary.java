package com.studyarc.use_case.track_plan;
//since there is not userinput data when executes, no parameter needede.

public interface TrackPlanInputBoundary {
    //execute the login usecase
    void execute(TrackPlanInputData trackPlanInputData);

    void execute(TrackPlanSavingInputData savingdata);
}
