package com.studyarc.use_case.delete_plan;

import com.studyarc.entity.StudyPlan;
import com.studyarc.use_case.track_plan.TrackPlanDataAccessinterface;

import java.util.ArrayList;

/***
 * Interactor class for the Delete Plan use case
 */


public class DeletePlanInteractor implements DeletePlanInputBoundary {
    private final DeletePlanOutputBoundary presenter;
    private final TrackPlanDataAccessinterface dataAccessTool;


    public DeletePlanInteractor(DeletePlanOutputBoundary presenter, TrackPlanDataAccessinterface dataAccessTool) {
        this.dataAccessTool = dataAccessTool;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeletePlanInputData input) {
        StudyPlan plan = input.getPlan();
        ArrayList<StudyPlan> plans = dataAccessTool.getPlans();
        plans.remove(plan);
        dataAccessTool.save();
        presenter.ShowPlans(new DeletePlanOutputData(plan));
    }
}
