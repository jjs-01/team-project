package com.studyarc.use_case.track_plan;

public interface TrackPlanOutputBoundary {
    //if the user has plans then show the plans -> Mainflow
    void prepareShowPlans(TrackPlanOutputData outputData);

    //if the use does not have plans, show a redirect link in the screen -> alternative flow
    void parepareShowRedirect();
}
