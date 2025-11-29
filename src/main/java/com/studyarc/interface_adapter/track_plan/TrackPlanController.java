package com.studyarc.interface_adapter.track_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.track_plan.TrackPlanInputBoundary;
import com.studyarc.use_case.track_plan.TrackPlanInputData;
import com.studyarc.use_case.track_plan.TrackPlanSavingInputData;

import java.util.ArrayList;


public class TrackPlanController {
    final TrackPlanInputBoundary trackplaninteractor;

    public TrackPlanController(TrackPlanInputBoundary trackplaninteractor){
        this.trackplaninteractor = trackplaninteractor;
    }


    public void execute(String username){

        TrackPlanInputData trackPlanInputData = new TrackPlanInputData(username);
        this.trackplaninteractor.execute(trackPlanInputData);
    }

    public void execute(ArrayList<StudyPlan> plans, String username){
        TrackPlanSavingInputData savingData = new TrackPlanSavingInputData(plans, username);
        this.trackplaninteractor.execute(savingData);
    }

}
