package com.studyarc.use_case.track_plan;
//
//use case Interactor for tracking a plan
// To do next:
// 1. Finish Implementing show plans on the view ✅


import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

        //return the studyplans of the current user
        ArrayList<StudyPlan> listOfPlans = this.getPlanTool.getPlans();

        TrackPlanOutputData outputData = new TrackPlanOutputData(username, listOfPlans);
        presenter.prepareShowPlans(outputData);
        if (listOfPlans.isEmpty()) {
            System.out.println("interactor executes for emptyplans");
            presenter.prepareShowRedirect();
        } else {
            System.out.println("interactor executes");
            presenter.prepareShowPlans(outputData);
        }
    }

    public void execute(TrackPlanSavingInputData savingInputData){
        ArrayList<StudyPlan> plans = savingInputData.getPlans();
        Set<String> planTitles = new HashSet<>();
        for (StudyPlan plan : plans) {
            if (plan.getTitle().strip().isEmpty()) {
                presenter.prepareShowSavingResult(" Empty Plan Title! Not allowed!😡😡 ");
                return;
            }
            planTitles.add(plan.getTitle());
        }
        if (planTitles.size() == plans.size()) {
            this.getPlanTool.saveAllPlansForUser(plans);
            presenter.prepareShowSavingResult(" Save complete! ");
        } else {
            presenter.prepareShowSavingResult(" Oops!!Can not have repetitive plans! ");
        }
    }
}
