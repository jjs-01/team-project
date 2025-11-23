package com.studyarc.use_case.track_plan;
//
//use case Interactor for tracking a plan
// To do next:
// 1. Finish Implementing show plans on the view
// 2. implement dataaccesstool to get plans from a user.


import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;
import java.util.List;

public class TrackPlanInteractor implements TrackPlanInputBoundary {
    final TrackPlanOutputBoundary presenter;
    final TrackPlanDataAccessinterface getPlanTool;

    public TrackPlanInteractor(TrackPlanOutputBoundary presenter, TrackPlanDataAccessinterface getPlanTool) {
        this.presenter = presenter;
        this.getPlanTool = getPlanTool;
    }

    @Override
    public void execute(TrackPlanInputData inputData) {


        String username = inputData.getUsername();
        ArrayList<StudyPlan> listofplans = this.getPlanTool.getPlans(username);
        TrackPlanOutputData trackPlanOutputData = new TrackPlanOutputData(username, listofplans);

        if (listofplans.isEmpty()) {
            System.out.println("interactor executes for emptyplans");
            presenter.parepareShowRedirect(trackPlanOutputData);
        } else {
            System.out.println("interactor executes");
            presenter.prepareShowPlans(trackPlanOutputData);
        }


    }
}
