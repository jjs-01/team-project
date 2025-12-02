package com.studyarc.use_case.track_plan;

import com.studyarc.entity.StudyPlan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * use case Interactor for TrackPlan usecase
 */

public class TrackPlanInteractor implements TrackPlanInputBoundary {
    final TrackPlanOutputBoundary presenter;
    final TrackPlanDataAccessInterface getPlanTool;

    public TrackPlanInteractor(TrackPlanOutputBoundary presenter, TrackPlanDataAccessInterface getPlanTool) {
        this.presenter = presenter;
        this.getPlanTool = getPlanTool;
    }

    @Override
    public void execute(TrackPlanInputData inputData) {

        String username = inputData.getUsername();

        //return the study plans of the current user
        List<StudyPlan> listOfPlans = this.getPlanTool.getPlans();

        TrackPlanOutputData outputData = new TrackPlanOutputData(username, listOfPlans);
        presenter.prepareShowPlans(outputData);
        if (listOfPlans.isEmpty()) {
            presenter.prepareShowRedirect();
        } else {
            presenter.prepareShowPlans(outputData);
        }
    }

    public void execute(TrackPlanSavingInputData savingInputData){
        List<StudyPlan> plans = savingInputData.getPlans();
        Set<String> planTitles = new HashSet<>();
        for (StudyPlan plan : plans) {
            if (plan.getTitle().isBlank()) {
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
