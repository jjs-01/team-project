package com.studyarc.interface_adapter.track_plan;

import com.studyarc.use_case.track_plan.TrackPlanInputBoundary;
import com.studyarc.use_case.track_plan.TrackPlanInputData;


public class TrackPlanController {
    final TrackPlanInputBoundary trackplaninteractor;

    public TrackPlanController(TrackPlanInputBoundary trackplaninteractor){
        this.trackplaninteractor = trackplaninteractor;
    }


    public void execute(String username){

        TrackPlanInputData trackPlanInputData = new TrackPlanInputData(username);
        this.trackplaninteractor.execute(trackPlanInputData);
    }

}
