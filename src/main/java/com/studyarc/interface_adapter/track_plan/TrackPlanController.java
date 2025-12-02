package com.studyarc.interface_adapter.track_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.track_plan.TrackPlanInputBoundary;
import com.studyarc.use_case.track_plan.TrackPlanInputData;
import com.studyarc.use_case.track_plan.TrackPlanSavingInputData;

import java.util.List;

/***
 * Controller class for the Track Plan use case
 * This class is responsible for handling the user input and invoking the Track Plan use case interactor.
 */

public class TrackPlanController {
    final TrackPlanInputBoundary trackPlanInteractor;

    public TrackPlanController(TrackPlanInputBoundary trackPlanInteractor){
        this.trackPlanInteractor = trackPlanInteractor;
    }


    public void execute(String username){

        TrackPlanInputData trackPlanInputData = new TrackPlanInputData(username);
        this.trackPlanInteractor.execute(trackPlanInputData);
    }

    public void execute(List<StudyPlan> plans, String username){
        TrackPlanSavingInputData savingData = new TrackPlanSavingInputData(plans, username);
        this.trackPlanInteractor.execute(savingData);
    }

}
